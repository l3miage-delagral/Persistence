package fr.uga.miage.m1.exceptions;

public class LocationException extends Exception {

    /**
     */
    private static final long serialVersionUID = 8091820514495097420L;

    public LocationException() {
        super();
    }

    public LocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocationException(String message) {
        super(message);
    }

    public LocationException(Throwable cause) {
        super(cause);
    }
}