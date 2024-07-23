package br.com.healthemed.healthhuman.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DoctorNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4039534595214046018L;
	
	public DoctorNotFoundException() {
		super();
	}
	
	public DoctorNotFoundException(String reason) {
		super(reason);
	}

}
