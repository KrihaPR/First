package com.example.publisher;

import com.example.event.EmailEvent;
import javafx.application.Application;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EmailPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    EmailPublisher(ApplicationEventPublisher publisher){
        applicationEventPublisher = publisher;
    }

    public void publishEmailEvent(EmailEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    public void publishMsgEvent(String msg) {
        applicationEventPublisher.publishEvent(msg);
    }
}
