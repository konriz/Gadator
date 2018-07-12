package com.gadator.users.exceptions;

public class WrongPasswordException extends Exception {

    public WrongPasswordException()
    {
        super("Wrong password");
    }
}
