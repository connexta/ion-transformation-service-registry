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
package com.connexta.transformation.api;

import org.springframework.amqp.core.Message;

/** Represents an AMQP producer. */
public interface ServiceRegistryProducer {

  /**
   * Publishes an AMQP message in a Message object format to a queue.
   *
   * @param transformRequest an AMQP message on the queue representing a transform request
   */
  void publishToQueue(Message transformRequest);

  /**
   * Publishes an AMQP message in a byte array format to a queue.
   *
   * @param transformRequest an AMQP message on the queue representing a transform request
   */
  void publishToQueue(byte[] transformRequest);
}
