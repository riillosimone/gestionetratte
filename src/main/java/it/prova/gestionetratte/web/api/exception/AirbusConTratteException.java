package it.prova.gestionetratte.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class AirbusConTratteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AirbusConTratteException(String message) {
		super(message);
	}
}
