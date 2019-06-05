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

import com.connexta.transformation.api.ServiceRegistryConsumer;
import com.connexta.transformation.api.ServiceRegistryProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class ServiceRegistryConsumerImpl implements ServiceRegistryConsumer {
  private final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryConsumerImpl.class);
  private final AmqpTemplate amqpTemplate;
  private final ServiceRegistryProducer producer;

  public ServiceRegistryConsumerImpl(AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
    this.producer = new ServiceRegistryProducerImpl(amqpTemplate);
  }

  @Override
  public void consumeFromQueue(Message transformRequest) {
    LOGGER.info(String.format("%s", transformRequest.toString()));

    // @TODO - do work (query database)

    ServiceRegistryProducer producer = new ServiceRegistryProducerImpl(amqpTemplate);
    producer.publishToQueue(transformRequest);
  }

  @Override
  public void consumeFromQueue(byte[] transformRequest) {
    //    JSONObject requestJson = new JSONObject(new String(transformRequest));
    //    String mimeType = requestJson.getString("mimeType");

    //    Message mimeType = jsonMessageConverter.toMessage(transformRequest, null);

    //    LOGGER.info(String.format("Querying for mime type '%s'", mimeType.toString()));

    LOGGER.info(String.format("%s", transformRequest.toString()));

    // @TODO - do work (query database)

    //    ServiceRegistryProducer producer = new ServiceRegistryProducerImpl(amqpTemplate);
    this.producer.publishToQueue(transformRequest);
  }
}
