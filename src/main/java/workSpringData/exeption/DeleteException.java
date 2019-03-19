package workSpringData.exeption;

public class DeleteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeleteException(String message) {
        super(message);
    }
}