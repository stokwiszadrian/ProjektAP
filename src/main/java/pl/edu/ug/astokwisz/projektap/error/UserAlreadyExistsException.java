package pl.edu.ug.astokwisz.projektap.error;

import java.io.Serial;

public final class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5861310537366287163L;

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(final String message) {
        super(message);
    }

    public UserAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

}
