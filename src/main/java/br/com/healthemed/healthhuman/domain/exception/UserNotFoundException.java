package br.com.healthemed.healthhuman.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4039534595214046018L;
	
	public UserNotFoundException() {
		super("User not found");
	}
	
	public UserNotFoundException(String reason) {
		super(reason);
	}

}
