package br.com.healthemed.healthhuman.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StatusNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4039534595214046018L;
	
	public StatusNotFoundException() {
		super("Status desconhecido");
	}
	
	public StatusNotFoundException(String reason) {
		super(reason);
	}

}
