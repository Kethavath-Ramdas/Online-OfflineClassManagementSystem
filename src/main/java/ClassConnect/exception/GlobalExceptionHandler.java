package ClassConnect.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ClassConnect.util.ResponseStructure;
@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	ResponseStructure responseStructure;
	
    @ExceptionHandler(RegisterAlreadyExistsException.class)
    public ResponseEntity<ResponseStructure<String>> handleRegisterException(
    		RegisterAlreadyExistsException ex) {
        ResponseStructure<String> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
        responseStructure.setMessage("Registration Failed");
        responseStructure.setData(ex.getMessage());
        return new ResponseEntity<>(responseStructure, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidLoginCredentialsException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidLogin(
            InvalidLoginCredentialsException ex) {
        ResponseStructure<String> responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        responseStructure.setMessage(ex.getMessage());
        responseStructure.setData("There is No data Login with Valid Login Creditial ");
        return new ResponseEntity<>(responseStructure, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AttendanceNotFoundException.class)
  public  ResponseEntity<ResponseStructure<String>> handleInvalidUserId(AttendanceNotFoundException att)
    {
    	 ResponseStructure<String> responseStructure = new ResponseStructure<>();
    	 responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
    	 responseStructure.setMessage(att.getMessage());
    	 responseStructure.setData("Enter Valid UserId To Show Attandance");
    	 return new ResponseEntity<>(responseStructure, HttpStatus.NOT_FOUND); 
    }  
    @ExceptionHandler(ClassCreationException.class)
    public ResponseEntity<ResponseStructure<String>> handleClassCreation(
            ClassCreationException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Class creation failed");
        response.setData(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    
}
