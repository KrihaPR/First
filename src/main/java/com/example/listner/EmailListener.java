package com.example.listner;

import com.example.event.EmailEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @Async
    @EventListener
    void sendMsgEvent(EmailEvent emailEvent) {
        System.out.println("==EmailListener 1 ==="+emailEvent.getMessage());
    }

    @Async
    @EventListener
    void sendMsgEvent(String message) {
        System.out.println("==EmailListener 2 ==="+message);
    }

}
