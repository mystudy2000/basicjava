package webapp.exceptions;

public class EmptyException extends StorageException {
    public EmptyException(String message, int errCode) {
        super(message, errCode);
    }
}

