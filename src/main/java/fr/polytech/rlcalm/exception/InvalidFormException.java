package fr.polytech.rlcalm.exception;

public class InvalidFormException extends RuntimeException {

    public InvalidFormException(String error) {
        super("Formulaire invalide" + (error == null ? " !" : " : " + error));
    }
}
