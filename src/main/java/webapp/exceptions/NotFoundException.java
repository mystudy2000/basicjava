package webapp.exceptions;

public class NotFoundException extends StorageException {
    public NotFoundException(String message, int errCode) {
        super(message, errCode);
    }
}
