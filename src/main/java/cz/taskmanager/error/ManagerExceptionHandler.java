package cz.taskmanager.error;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ManagerExceptionHandler extends ResponseEntityExceptionHandler  {

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ IllegalStateException.class })
	public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
