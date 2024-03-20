package com.push;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String key;

    private final String queueName = "send.alarm";

    // queue name
    @Bean
    Queue alarmQueue() {
        return new Queue(queueName,false);
    }

    // topic exchange
    @Bean
    TopicExchange alarmTopicExchange(){
        return new TopicExchange("send.alarm.#");
    }

    // binding
    @Bean
    Binding topicBinding(@Qualifier("alarmTopicExchange") TopicExchange signTopicExchange, @Qualifier("alarmQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(signTopicExchange).with(key);
    }


    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    ConnectionFactory connectionConfig() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);

        return connectionFactory;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     *  RabbitMQ에서 메시지를 수신하고 처리하는 데 사용되는 Spring AMQP의 컨테이너
     *  큐에 메시지가 도착하면 해당 메시지를 소비하여 등록된 리스너에게 전달하고, 리스너에서 메시지를 처리하는 역할
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionConfig());
        container.setQueueNames(queueName);
        container.setConcurrentConsumers(5); // 병렬 처리 수 설정 - default는 1개
        container.setMaxConcurrentConsumers(10); // 최대 병렬 처리 수 설정
        container.setReceiveTimeout(10000L); // 최대 시간
        return container;
    }
}
