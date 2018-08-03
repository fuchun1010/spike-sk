package com.tank.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Receiver {

  //@KafkaListener(topics = "${spring.kafka.customTopic.test_topic}")
  @KafkaListener(id = "receiver-api",
      topicPartitions =
          { @TopicPartition(topic = "${spring.kafka.customTopic.test_topic}",
              partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")),
              })
  public void receive(ConsumerRecord<String,String> record) {
    System.out.println("***********"+record.value());
  }


}
