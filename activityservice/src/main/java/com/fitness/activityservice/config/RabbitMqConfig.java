package com.fitness.activityservice.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Bean
    public Queue activityQueue() {
        return new Queue(queue, true);  // Ensure durability of the queue
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(exchange, true, false);  // Exchange with durability
    }

    @Bean
    public Binding binding(Queue activityQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(activityQueue).to(fanoutExchange);  // Bind queue to the exchange
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();  // Convert messages to JSON
    }
}
