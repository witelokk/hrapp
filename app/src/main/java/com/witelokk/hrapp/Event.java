package com.witelokk.hrapp;

import javax.annotation.Nullable;

public class Event<T> {
    private final T content;
    private boolean hasBeenHandled = false;

    public Event(T content) {
        this.content = content;
    }

    @Nullable
    public T getContent() {
        if (hasBeenHandled)
            return null;
        else {
            hasBeenHandled = true;
            return content;
        }
    }
}
