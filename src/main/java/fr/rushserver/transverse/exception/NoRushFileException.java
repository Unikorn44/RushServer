package fr.rushserver.transverse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoRushFileException extends Exception {

	private static final long serialVersionUID = 477884298847134711L;

	public NoRushFileException() {
        super();
    }
    public NoRushFileException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoRushFileException(String message) {
        super(message);
    }
    public NoRushFileException(Throwable cause) {
        super(cause);
    }
}
