package ru.varino.lab1.resource;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;

@ApplicationScoped
public class ChangeEvents {

    private Sse sse;
    private SseBroadcaster broadcaster;

    public synchronized void subscribe(Sse sse, SseEventSink sink) {
        if (broadcaster == null) {
            this.sse = sse;
            broadcaster = sse.newBroadcaster();
        }
        broadcaster.register(sink);
    }

    public synchronized void publish() {
        if (broadcaster != null) {
            broadcaster.broadcast(sse.newEvent("changed", "reload"));
        }
    }

    @PreDestroy
    public synchronized void close() {
        if (broadcaster != null) {
            broadcaster.close();
        }
    }
}
