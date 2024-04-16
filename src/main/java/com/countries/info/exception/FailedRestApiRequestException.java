package com.countries.info.exception;

public class FailedRestApiRequestException extends RuntimeException {
    public FailedRestApiRequestException (String exceptionMessage) {
        super(exceptionMessage);
    }
}
