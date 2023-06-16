package com.civip.csyy.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/12
 */
@Component
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerConfig {
    private String broker;
    private Map<String,String> topic;
    private String group;
    private String username;
    private String password;

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public Map<String, String> getTopic() {
        return topic;
    }

    public void setTopic(Map<String, String> topic) {
        this.topic = topic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  String[] getConsumerTopics() {
        return getTopic().values().toArray(new String[0]);
    }

    public String getMsgTypeByTopic(String topic){
        return getTopic().entrySet().stream().filter(t -> t.getValue().equals(topic)).map(Map.Entry::getKey).findFirst().orElse(null);
    }
}
