package me.benjozork.onyx.backend.exception;

/**
 * @author Benjozork
 */
public class DuplicateElementIdentifierException extends IllegalArgumentException {

    public DuplicateElementIdentifierException() {
        super();
    }

    public DuplicateElementIdentifierException(String s) {
        super(s);
    }

}
