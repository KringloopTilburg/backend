package kringlooptilburg.nl.productimageservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.product-image}")
    private String productImageQueueName;

    @Value("${rabbitmq.exchange.product-image}")
    private String productImageExchangeName;

    @Value("${rabbitmq.routing-key.product-image}")
    private String productImageRoutingKeyName;

    @Bean
    public Queue productImageQueue() {
        return new Queue(productImageQueueName);
    }

    @Bean
    public TopicExchange productImageExchange() {
        return new TopicExchange(productImageExchangeName);
    }

    @Bean
    public Binding productImageBinding(Queue productImageQueue, TopicExchange productImageExchange) {
        return BindingBuilder.
                bind(productImageQueue)
                .to(productImageExchange)
                .with(productImageRoutingKeyName);
    }
}
