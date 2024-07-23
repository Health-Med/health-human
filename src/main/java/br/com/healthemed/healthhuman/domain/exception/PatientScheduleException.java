package br.com.healthemed.healthhuman.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PatientScheduleException extends RuntimeException {

	private static final long serialVersionUID = -4039534595214046018L;
	
	public PatientScheduleException() {
		super("Impossível escolher agenda");
	}
	
	public PatientScheduleException(String reason) {
		super(reason);
	}

}
