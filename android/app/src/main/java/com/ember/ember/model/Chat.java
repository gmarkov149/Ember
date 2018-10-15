package com.ember.ember.model;

public class Chat {
    private boolean outgoing;
    private String message;

    public Chat(boolean outgoing, String message) {
        this.outgoing = outgoing;
        this.message = message;
    }

    public boolean isOutgoing() {
        return outgoing;
    }

    public String getMessage() {
        return message;
    }
}
