package com.fitness.activityservice.config;

import org.springframework.amqp.core.*;
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
    public FanoutExchange fitnessExchange() {
        return new FanoutExchange(exchange, true, false); // durable, not auto-delete
    }

    @Bean
    public Queue activityQueue() {
        return new Queue(queue, true); // durable queue
    }

    @Bean
    public Binding activityBinding(Queue activityQueue, FanoutExchange fitnessExchange) {
        return BindingBuilder.bind(activityQueue).to(fitnessExchange); // No routing key for Fanout
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(); // Convert messages to/from JSON
    }
}
