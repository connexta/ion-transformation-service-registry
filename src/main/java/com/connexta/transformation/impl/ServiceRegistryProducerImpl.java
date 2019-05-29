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
package com.connexta.transformation.impl;

import com.connexta.transformation.api.ServiceRegistryProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import com.connexta.transformation.AmqpConfiguration;

@Component
public class ServiceRegistryProducerImpl implements ServiceRegistryProducer {
  private final ApplicationContext context = new AnnotationConfigApplicationContext(
      AmqpConfiguration.class);
  private final AmqpTemplate amqpTemplate = context.getBean(RabbitTemplate.class);

  public ServiceRegistryProducerImpl() {
    // No-arg constructor
  }

  @Override
  public void publishToQueue(byte[] transformRequest) {
    this.amqpTemplate.convertAndSend(transformRequest);
  }
}
