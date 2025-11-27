package com.rabbitmq.registration.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "minhaFila";
    public static final String EXCHANGE_NAME = "minhaExchange";
    public static final String ROUTING_KEY = "minha.chave";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true); // true = fila persistente
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
