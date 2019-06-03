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

import com.connexta.transformation.AmqpConfiguration;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import com.connexta.transformation.api.ServiceRegistryConsumer;
import com.connexta.transformation.api.ServiceRegistryProducer;

@Component
public class ServiceRegistryConsumerImpl implements ServiceRegistryConsumer {
  private final ApplicationContext context = new AnnotationConfigApplicationContext(
      AmqpConfiguration.class);

  private final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryConsumerImpl.class);
  private final Jackson2JsonMessageConverter jsonMessageConverter =
      context.getBean(Jackson2JsonMessageConverter.class);

  public ServiceRegistryConsumerImpl() {
    // No-arg constructor
  }

  @Override
  public void consumeFromQueue(byte[] transformRequest) throws InterruptedException {
//    JSONObject requestJson = new JSONObject(new String(transformRequest));
//    String mimeType = requestJson.getString("mimeType");

    Message mimeType = jsonMessageConverter.toMessage(transformRequest, null);

    LOGGER.info(String.format("Querying for mime type '%s'", mimeType.toString()));

    // @TODO - do work (query database)
    Thread.sleep(5000);  // @TODO - remove this

    ServiceRegistryProducer producer = new ServiceRegistryProducerImpl();
    producer.publishToQueue(transformRequest);
  }
}
