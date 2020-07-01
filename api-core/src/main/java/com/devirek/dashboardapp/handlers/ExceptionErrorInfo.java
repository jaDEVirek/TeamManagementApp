package com.devirek.dashboardapp.handlers;

/**
 * ExceptionErrorInfo immutable class is used to holding output information about an Error
 */
public final class ExceptionErrorInfo {
    private final String error;

    public ExceptionErrorInfo(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
