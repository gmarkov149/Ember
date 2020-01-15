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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Chat other = (Chat) obj;
        return this.outgoing == other.outgoing && this.message.equals(other.message) && this.date.equals(other.date);
    }
}
