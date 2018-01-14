package webapp.exceptions;
public class StorageException extends Exception {
    private int errCode;

    public StorageException(String message, int errCode) {
        super(message);
        this.errCode = errCode;
    }

    public int getErrocde() {
        return errCode;
    }
    public void setErrCode(int errCode) {this.errCode = errCode;}

}