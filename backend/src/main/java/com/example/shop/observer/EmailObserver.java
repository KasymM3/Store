//package com.example.shop.observer;
//
//import com.example.shop.state.OrderContext;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//public class EmailObserver implements OrderObserver {
//    private final JavaMailSender mailSender;
//    private final String recipient;
//
//    public EmailObserver(JavaMailSender mailSender, String recipient) {
//        this.mailSender = mailSender;
//        this.recipient = recipient;
//    }
//
//    @Override
//    public void update(OrderContext order) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(recipient);
//        msg.setSubject("Order #" + order.getId() + " → " + order.getStateName());
//        msg.setText("Your order #" + order.getId()
//                + " is now in state: " + order.getStateName());
//
//        try {
//            mailSender.send(msg);
//            System.out.printf("[Email] sent to %s for Order #%d%n",
//                    recipient, order.getId());
//        } catch (MailException ex) {
//            System.err.printf("[EmailObserver] failed for Order #%d: %s%n",
//                    order.getId(), ex.getMessage());
//        }
//    }
//}
