package com.github.benfradet.spark.kafka08.writer

import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer

import scala.collection.mutable

/** Cache of [[KafkaProducer]]s */
private[writer] object KafkaProducerCache {
  private val producers = mutable.HashMap.empty[Properties, KafkaProducer[_, _]]

  /**
   * Retrieve a [[KafkaProducer]] in the cache or create a new one
   * @param producerConfig properties for a [[KafkaProducer]]
   * @return a [[KafkaProducer]] already in the cache
   */
  def getProducer[K, V](producerConfig: Properties): KafkaProducer[K, V] = {
    producers.getOrElse(producerConfig, {
      val producer = new KafkaProducer[K, V](producerConfig)
      producers(producerConfig) = producer
      producer
    }).asInstanceOf[KafkaProducer[K, V]]
  }
}
