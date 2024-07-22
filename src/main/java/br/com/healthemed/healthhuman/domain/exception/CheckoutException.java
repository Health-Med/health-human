package br.com.healthemed.healthhuman.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Impossível escolher agenda")
public class CheckoutException extends RuntimeException {

	private static final long serialVersionUID = -4039534595214046018L;
	
	public CheckoutException() {
		super();
	}
	
	public CheckoutException(String reason) {
		super(reason);
	}

}
