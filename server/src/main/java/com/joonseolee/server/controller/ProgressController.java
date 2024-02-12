package com.joonseolee.server.controller;

import com.joonseolee.server.container.SseContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ProgressController {

    private final AtomicInteger progressValue = new AtomicInteger(0);

    @GetMapping(value = "/progress", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        SseEmitter emitter = new SseEmitter();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            try {
                // 가산된 값을 클라이언트로 보내기
                int value = (int) (Math.random() * 100);
                emitter.send(SseEmitter.event().name("connect").data(String.valueOf(value)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 1, 1, TimeUnit.SECONDS);

        emitter.onCompletion(executorService::shutdown);
        return ResponseEntity.ok(emitter);
    }
}
