package br.com.healthemed.healthhuman.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ScheduleException extends RuntimeException {

	private static final long serialVersionUID = -4039534595214046018L;
	
	public ScheduleException() {
		super();
	}
	
	public ScheduleException(String reason) {
		super(reason);
	}

}
