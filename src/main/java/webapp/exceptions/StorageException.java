package webapp.exceptions;
public class StorageException extends Exception {
    private int errCode;
/**
   Exceptions description, errCode
    "Search key not found",700
    "Search key duplicated",800
    "Storage is empty!",900
    "Storage overflow!",1000
 */
    public StorageException(String message, int errCode) {
        super(message);
        this.errCode = errCode;
    }

    public int getErrocde() {
        return errCode;
    }

    public void setErrCode(int errCode) {this.errCode = errCode;}

}