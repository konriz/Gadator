package com.gadator.exception;

public class NoConversationException extends Exception {

    public NoConversationException(String name)
    {
        super("No conversation named " + name);
    }
}
