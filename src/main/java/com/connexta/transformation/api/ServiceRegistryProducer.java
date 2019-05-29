package com.connexta.transformation.api;

public interface ServiceRegistryProducer {
  void publishToQueue(byte[] transformRequest);
}
