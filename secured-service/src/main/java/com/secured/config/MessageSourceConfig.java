package com.secured.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix="messagesource")
@Getter
@Setter
public class MessageSourceConfig {
    private List<String> files;
}
