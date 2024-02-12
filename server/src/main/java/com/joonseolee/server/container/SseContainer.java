package com.joonseolee.server.container;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SseContainer {

    private final List<SseEmitter> emitters = new ArrayList<>();

    public SseEmitter registerEmitter() {
        SseEmitter emitter = new SseEmitter();
        try {
            emitter.send(SseEmitter.event().name("connect").data(10));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        emitters.add(emitter);

        return emitter;
    }

    public void updateProgress() {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("connect").data(10));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });

        emitters.removeAll(deadEmitters);
    }
}
