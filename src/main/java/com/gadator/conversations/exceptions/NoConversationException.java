package com.gadator.conversations.exceptions;

public class NoConversationException extends Exception {

    public NoConversationException(String name)
    {
        super("No conversation named " + name);
    }
}
