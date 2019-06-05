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
