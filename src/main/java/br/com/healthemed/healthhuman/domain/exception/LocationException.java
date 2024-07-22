package br.com.healthemed.healthhuman.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_GATEWAY, reason = "Impossível consultar endereço")
public class LocationException extends RuntimeException {

	private static final long serialVersionUID = -4039534595214046018L;
	
	public LocationException() {
		super();
	}
	
	public LocationException(String reason) {
		super(reason);
	}

}
