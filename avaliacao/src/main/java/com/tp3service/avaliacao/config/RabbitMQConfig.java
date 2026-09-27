package com.tp3service.avaliacao.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${avaliacao.rabbitmq.exchange}")
    private String exchange;

    @Value("${avaliacao.rabbitmq.routing-key}")
    private String routingKey;

    @Value("${avaliacao.rabbitmq.queue}")
    private String queue;

    @Bean
    public DirectExchange avaliacaoExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue avaliacaoQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public Binding avaliacaoBinding() {
        return BindingBuilder
                .bind(avaliacaoQueue())
                .to(avaliacaoExchange())
                .with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
