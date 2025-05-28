package com.example.shop.controller;

import com.example.shop.dto.AddToCartRequest;
import com.example.shop.dto.CartItem;
import com.example.shop.model.Product;
import com.example.shop.service.OrderService;
import com.example.shop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@SessionAttributes("cart")
public class CartRestController {

    private final ProductService productService;
    private final OrderService orderService;

    public CartRestController(ProductService productService,
                              OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    // 1) При первом запросе создаём пустую корзину

    @ModelAttribute("cart")
    public List<CartItem> cart() {
        return new ArrayList<>();
    }


    // 2) GET /api/cart — возвращаем текущее состояние корзины
//    @GetMapping
//    public List<CartItem> getCart(@ModelAttribute("cart") List<CartItem> cart) {
//        return cart;
//    }
//
//    // 3) POST /api/cart — добавляем продукт по его id
//    @PostMapping
//    public List<CartItem> addToCart(
//            @ModelAttribute("cart") List<CartItem> cart,
//            @RequestBody AddToCartRequest req
//    ) {
//        Long productId = req.getProductId();
//        int qty = (req.getQuantity() != null ? req.getQuantity() : 1);
//
//        Product prod = productService.getById(productId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        // если уже есть — увеличим количество
//        for (CartItem item : cart) {
//            if (item.getProductId().equals(productId)) {
//                item.setQuantity(item.getQuantity() + qty);
//                return cart;
//            }
//        }
//
//        // иначе добавим новый элемент
//        CartItem newItem = new CartItem(
//                prod.getId(),
//                prod.getName(),
//                prod.getPrice(),
//                qty
//        );
//        cart.add(newItem);
//        return cart;
//    }

    @DeleteMapping("/{productId}")
    public List<CartItem> removeFromCart(
            @ModelAttribute("cart") List<CartItem> cart,
            @PathVariable Long productId
    ) {
        cart.removeIf(i -> i.getProductId().equals(productId));
        return cart;
    }
    // 4) POST /api/cart/checkout — оформляем заказ и сбрасываем корзину
    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null && !cart.isEmpty()) {
            for (CartItem item : cart) {
                // получаем свежий Product, чтобы взять у него категорию и цену
                Product p = productService.getById(item.getProductId())
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Product not found: " + item.getProductId()));
                String type = "Digital".equals(p.getCategory()) ? "digital" : "physical";
                // если в корзине было несколько штук — создаём столько заказов
                for (int i = 0; i < item.getQuantity(); i++) {
                    orderService.createOrder(type, p.getPrice());
                }
            }
            session.removeAttribute("cart");
        }
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public List<CartItem> getCart(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @PostMapping
    public List<CartItem> addToCart(@RequestBody Map<String, Integer> payload,
                                       HttpSession session) {

        Integer productIdInt = payload.get("productId");
        Long productId = productIdInt.longValue();                   // ← конвертация
        Product p = productService.getById(productId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product not found with id " + productId
                        )
                );
        CartItem item = new CartItem(
                p.getId(),
                p.getName(),
                p.getPrice(),   // убедитесь, что getPrice() возвращает Double
                1               // количество
        );


        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        cart.add(item);
        session.setAttribute("cart", cart);
        return cart;
    }
    @PostMapping("/order/{productId}")
    public ResponseEntity<List<CartItem>> orderOneItem(
            @PathVariable Long productId,
            HttpSession session
    ) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        Iterator<CartItem> it = cart.iterator();
        while (it.hasNext()) {
            CartItem item = it.next();
            if (item.getProductId().equals(productId)) {
                // для каждого количества товара достаём Product и оформляем заказ
                Product p = productService.getById(item.getProductId())
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Product not found: " + productId));
                String type = "Digital".equals(p.getCategory()) ? "digital" : "physical";
                for (int i = 0; i < item.getQuantity(); i++) {
                    orderService.createOrder(type, p.getPrice());
                }
                it.remove();
                break;
            }
        }
        session.setAttribute("cart", cart);
        return ResponseEntity.ok(cart);
    }

}
