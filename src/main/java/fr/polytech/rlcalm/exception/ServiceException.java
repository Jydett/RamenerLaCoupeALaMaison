package fr.polytech.rlcalm.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String error) {
        super(error);
    }
}
