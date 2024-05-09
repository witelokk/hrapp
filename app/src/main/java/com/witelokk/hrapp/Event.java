package com.witelokk.hrapp;

public class Event<T> {
    private final T content;
    private boolean hasBeenHandled = false;

    public Event(T content) {
        this.content = content;
    }

    public T getContent() {
        if (hasBeenHandled)
            return null;
        else {
            hasBeenHandled = true;
            return content;
        }
    }
}
