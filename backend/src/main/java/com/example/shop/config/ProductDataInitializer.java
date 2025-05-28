package com.example.shop.config;

import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductDataInitializer implements CommandLineRunner {
    private final ProductRepository repo;
    public ProductDataInitializer(ProductRepository repo) { this.repo = repo; }

    @Override
    public void run(String... args) throws Exception {
//        repo.save(new Product("Кофемашина", "Автоматическая с капучинатором", 129.99,
//                "https://ligabarshop.ru/upload/dev2fun.imagecompress/webp/iblock/a59/6iz8etkm6ui769wah0at6sx51z8d8l4e.webp", "Physical"));
//        repo.save(new Product("Наушники", "Беспроводные шумоподавляющие", 89.50,
//                "https://smartme.wiki/wp-content/uploads/2020/05/headphones-300x200-1.jpeg", "Physical"));
//        repo.save(new Product("Книга «Java для начинающих»", "Учебник по Java", 19.90,
//                "https://ir-3.ozone.ru/multimedia/wc1000/1023718303.jpg", "Physical"));
//
//        // Новые цифровые товары
//        repo.save(new Product("E-Book «Mastering Spring»", "Электронная книга в PDF", 29.99,
//                "https://avatars.mds.yandex.net/get-mpic/5189780/2a0000018ff7bdd83eb7c6b3d9c513e31af1/orig", "Digital"));
//        repo.save(new Product("Software License", "Лицензия на ПО", 49.99,
//                "https://i.pinimg.com/736x/5c/0a/96/5c0a9643e04754bf437e9e7a6dadd06f.jpg", "Digital"));
    }
}
