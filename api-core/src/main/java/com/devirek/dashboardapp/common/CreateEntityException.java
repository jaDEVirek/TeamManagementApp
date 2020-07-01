package com.devirek.dashboardapp.common;

/**
 * Class for handling exception on creating team data
 */
public class CreateEntityException extends RuntimeException {

    public CreateEntityException(Throwable cause) {
        super("Creating new team - failed.There is an error with object fields", cause);
    }
}
