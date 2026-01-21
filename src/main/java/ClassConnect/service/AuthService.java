package ClassConnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ClassConnect.dto.RegistrationRequest;
import ClassConnect.entity.User;
import ClassConnect.exception.RegisterAlreadyExistsException;
import ClassConnect.repository.UserRepository;
import ClassConnect.util.ResponseStructure;

@Service
public class AuthService {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Autowired
	    private ResponseStructure<User> responseStructure;
	    
//Register
	    public ResponseEntity<ResponseStructure<User>> register(
	            RegistrationRequest request) {
	        if (userRepository.existsByUsername(request.getUsername())) {
	            throw new RegisterAlreadyExistsException("Username already exists, try With another UserName");
	           }
	        User user = new User();
	        user.setUsername(request.getUsername());
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	        user.setRole("ROLE_USER");
	        User savedUser = userRepository.save(user);
	        responseStructure.setStatusCode(HttpStatus.CREATED.value());
	        responseStructure.setMessage("User registered successfully");
	        responseStructure.setData(savedUser);
	        return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	    }
	    // LOGIN 
	    public ResponseEntity<ResponseStructure<String>> loginSuccess(String token) {
	        ResponseStructure<String> responseStructure = new ResponseStructure<>();
	        responseStructure.setStatusCode(HttpStatus.OK.value());
	        responseStructure.setMessage("Login successful");
	        responseStructure.setData(token);
	        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	    }
	}