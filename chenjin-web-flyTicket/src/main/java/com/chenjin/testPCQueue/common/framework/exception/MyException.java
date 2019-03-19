package com.chenjin.testPCQueue.common.framework.exception;

public class MyException extends RuntimeException
{
    private static final long serialVersionUID = -455241119781868259L;

    public MyException()
    {
    }

    public MyException(String message)
    {
        super(message);
    }
}