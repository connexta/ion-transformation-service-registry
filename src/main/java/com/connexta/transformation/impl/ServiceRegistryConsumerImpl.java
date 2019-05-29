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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.connexta.transformation.api.ServiceRegistryConsumer;
import com.connexta.transformation.api.ServiceRegistryProducer;

@Component
public class ServiceRegistryConsumerImpl implements ServiceRegistryConsumer {
  private final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryConsumerImpl.class);

  public ServiceRegistryConsumerImpl() {
    // No-arg constructor
  }

  @Override
  public void consumeFromQueue(byte[] transformRequest) throws InterruptedException {
    JSONObject requestJson = new JSONObject(new String(transformRequest));
    String mimeType = requestJson.getString("mimeType");
    LOGGER.info(String.format("Querying for mime type '%s'", mimeType));

    // @TODO - do work (query database)
    Thread.sleep(5000);  // @TODO - remove this

    ServiceRegistryProducer producer = new ServiceRegistryProducerImpl();
    producer.publishToQueue(transformRequest);
  }
}
