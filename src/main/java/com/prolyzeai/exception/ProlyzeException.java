package com.prolyzeai.exception;

import lombok.Getter;


@Getter
public class ProlyzeException extends RuntimeException
{
    private ErrorType errorType;

    public ProlyzeException(ErrorType errorType)
    {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ProlyzeException(ErrorType errorType, String customMessage)
    {
        super(customMessage);
        this.errorType = errorType;
    }
}
