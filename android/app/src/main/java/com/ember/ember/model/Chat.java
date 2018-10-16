package com.ember.ember.model;

import java.util.Date;

public class Chat {
    private boolean outgoing;
    private String message;
    private Date date;

    public Chat(boolean outgoing, String message, Date date) {
        this.outgoing = outgoing;
        this.message = message;
        this.date = date;
    }

    public boolean isOutgoing() {
        return outgoing;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
