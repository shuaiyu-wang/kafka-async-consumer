package com.civip.csyy.kafka;

import com.civip.csyy.common.config.KafkaConsumerConfig;
import com.civip.csyy.common.enums.MsgTypeEnum;
import com.civip.csyy.kafka.handler.KafkaMsgHandlerDispatcher;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/12
 */
@Component
public class KafkaConsumer implements CommandLineRunner, DisposableBean {

    private volatile boolean running = true;
    private boolean refresh = false;
    private final CountDownLatch latch = new CountDownLatch(1);
    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private KafkaConsumerConfig kafkaConsumerConfig;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private KafkaMsgHandlerDispatcher kafkaMsgHandlerDispatcher;

    private void consume() throws Exception {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaConsumerConfig.getBroker());
        props.setProperty("group.id", kafkaConsumerConfig.getGroup());
        props.setProperty("enable.auto.commit", "false");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("max.poll.records", "500");
        props.setProperty("auto.offset.reset", "latest");// latest earliest
        props.setProperty("max.poll.interval.ms", "600000");
        props.setProperty("security.protocol", "SASL_PLAINTEXT");
        props.setProperty("sasl.mechanism", "SCRAM-SHA-256");
        props.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\""+kafkaConsumerConfig.getUsername()+"\" password=\""+kafkaConsumerConfig.getPassword()+"\";");
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList((kafkaConsumerConfig.getConsumerTopics())));
        try {
            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                // 只有poll到数据了才能拿到分区数
                if (refresh && !records.isEmpty()) {
                    Set<TopicPartition> assignments = consumer.assignment();
                    // 从分区末尾开始消费
                    consumer.seekToEnd(assignments);
                    refresh = false;
                    continue;
                }
                for (ConsumerRecord<String, String> record : records) {
                    String msgType = kafkaConsumerConfig.getMsgTypeByTopic(record.topic());
                    MsgTypeEnum msgTypeEnum = MsgTypeEnum.of(msgType);
                    if (!Objects.isNull(msgTypeEnum)) {
                        kafkaMsgHandlerDispatcher.getHandler(msgTypeEnum.getCode()).handle(record.value());
                    }
                }
                // 异步提交
                consumer.commitAsync();
            }
        } catch (Exception e) {
            logger.error("消费数据出现异常");
            throw e;
        } finally {
            try {
                // 同步提交
                consumer.commitSync();
            } catch (Exception e) {
                logger.error("消费者同步提交出现异常", e);
            } finally {
                consumer.close();
                latch.countDown();
                logger.info("kafka 消费线程安全关闭。");
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        Runnable r = ()->{
            try {
                consume();
            } catch (Exception e) {
                logger.error("kafka 消费程序发生未知异常导致中断，退出程序。", e);
                System.exit(1);
            }
        };
        threadPoolTaskExecutor.execute(r);
    }

    @Override
    public void destroy() throws Exception {
        this.running= false;
        latch.await();
    }
}
