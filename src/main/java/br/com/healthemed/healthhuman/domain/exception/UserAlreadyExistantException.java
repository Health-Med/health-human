package br.com.healthemed.healthhuman.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserAlreadyExistantException extends RuntimeException {

	private static final long serialVersionUID = -4039534595214046018L;
	
	public UserAlreadyExistantException() {
		super("User already existant");
	}
	
	public UserAlreadyExistantException(String user) {
		super("User already existant " + user);
	}

}
