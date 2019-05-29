/**
 * Copyright (c) Connexta
 *
 * <p>This is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details. A copy of the GNU Lesser General Public
 * License is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package com.connexta.transformation;

import com.connexta.transformation.api.ServiceRegistryQueueConstants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.connexta.transformation.api.ServiceRegistryConsumer;
import com.connexta.transformation.impl.ServiceRegistryConsumerImpl;

@Configuration
@EnableRabbit
@PropertySource("classpath:application.properties")
public class AmqpConfiguration {
  @Value("${spring.rabbitmq.host}")
  private String messageBrokerHost;

  @Value("${spring.rabbitmq.port}")
  private int messageBrokerPort;

  @Value("${spring.rabbitmq.virtual-host}")
  private String virtualHost;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;

  @Value("${spring.rabbitmq.template.exchange}")
  private String exchangeName;

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory =
        new CachingConnectionFactory(this.messageBrokerHost, this.messageBrokerPort);
    connectionFactory.setUsername(this.username);
    connectionFactory.setPassword(this.password);
    connectionFactory.setVirtualHost(this.virtualHost);
    connectionFactory.setPublisherReturns(true);
    connectionFactory.setPublisherConfirms(true);
    return connectionFactory;
  }

  @Bean
  public Queue requestQueue() {
    return new Queue(ServiceRegistryQueueConstants.REQUEST_QUEUE_NAME, true);
  }

  @Bean
  public Queue serviceQueue() {
    return new Queue(ServiceRegistryQueueConstants.SERVICE_QUEUE_NAME, true);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(this.exchangeName);
  }

  @Bean
  public Binding requestBinding() {
    return BindingBuilder.bind(this.requestQueue()).to(this.exchange())
        .with(ServiceRegistryQueueConstants.REQUEST_ROUTING_KEY);
  }

  @Bean
  public Binding serviceBinding() {
    return BindingBuilder.bind(this.serviceQueue()).to(this.exchange())
        .with(ServiceRegistryQueueConstants.SERVICE_ROUTING_KEY);
  }

  @Bean
  public AmqpTemplate amqpTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
    rabbitTemplate.setExchange(this.exchangeName);
    rabbitTemplate.setRoutingKey(ServiceRegistryQueueConstants.SERVICE_ROUTING_KEY);
    return rabbitTemplate;
  }

  @Bean
  public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(ServiceRegistryQueueConstants.REQUEST_QUEUE_NAME);
    container.setMessageListener(listenerAdapter);
    return container;
  }

  @Bean
  public MessageListenerAdapter listenerAdapter() {
    ServiceRegistryConsumer consumer = new ServiceRegistryConsumerImpl();
    return new MessageListenerAdapter(consumer, "consumeFromQueue");
  }
}
