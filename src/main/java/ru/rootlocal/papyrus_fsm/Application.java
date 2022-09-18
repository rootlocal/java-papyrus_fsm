package ru.rootlocal.papyrus_fsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private final Environment env;

    @Autowired
    public Application(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doMyStartAfterStartup() {
        log.info("Run ApplicationReadyEvent console application...");
        log.info("getActiveProfiles: {}", Arrays.toString(env.getActiveProfiles()));
    }
}
