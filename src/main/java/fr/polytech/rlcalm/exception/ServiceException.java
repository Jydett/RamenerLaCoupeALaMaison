package fr.polytech.rlcalm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ServiceException extends RuntimeException {
    private String error;
}
