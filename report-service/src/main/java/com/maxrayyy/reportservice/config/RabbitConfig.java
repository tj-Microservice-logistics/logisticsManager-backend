//package com.maxrayyy.reportservice.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//    public static final String ORDER_EXCHANGE = "order.exchange";
//    public static final String ORDER_STATISTICS_QUEUE = "order.statistics.queue";
//    public static final String ORDER_ROUTING_KEY = "order.status.changed";
//
//    @Bean
//    public DirectExchange orderExchange() {
//        return new DirectExchange(ORDER_EXCHANGE);
//    }
//
//    @Bean
//    public Queue orderStatisticsQueue() {
//        return new Queue(ORDER_STATISTICS_QUEUE);
//    }
//
//    @Bean
//    public Binding orderBinding() {
//        return BindingBuilder.bind(orderStatisticsQueue())
//                .to(orderExchange())
//                .with(ORDER_ROUTING_KEY);
//    }
//}