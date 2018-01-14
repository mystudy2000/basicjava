package webapp.exceptions;

public class DuplicateException extends StorageException {
    public DuplicateException(String message, int errCode) {
        super(message, errCode);
    }
}
