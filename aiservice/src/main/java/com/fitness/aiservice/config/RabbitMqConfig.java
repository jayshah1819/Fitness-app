package com.fitness.aiservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;


//If I am working MCP server then I don't need it.
@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.name}")
    private String queue;
//FanoutExchange
    @Bean
    public FanoutExchange fitnessExchange() {
        return new FanoutExchange(exchange, true, false); // durable, not auto-delete
    }
//Activity Queue
    @Bean
    public Queue activityQueue() {
        return new Queue(queue, true); // durable queue
    }
//Activity Binding
    @Bean
    public Binding activityBinding(Queue activityQueue, FanoutExchange fitnessExchange) {
        return BindingBuilder.bind(activityQueue).to(fitnessExchange);
        // Fanout exchange does not use routing key
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(); // Convert messages to/from JSON
    }
}
