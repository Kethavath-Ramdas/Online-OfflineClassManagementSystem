package ClassConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import ClassConnect.config.JwtUtils;
import ClassConnect.dto.LoginRequest;
import ClassConnect.dto.RegistrationRequest;
import ClassConnect.entity.User;
import ClassConnect.exception.InvalidLoginCredentialsException;
import ClassConnect.service.AuthService;
import ClassConnect.util.ResponseStructure;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<User>> register(
            @RequestBody RegistrationRequest request) {

        return authService.register(request);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> login(
            @RequestBody LoginRequest request) {
        try {
       authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            } 
        catch (Exception e) {
            throw new InvalidLoginCredentialsException("Invalid username or password" );
            }
        String token = jwtUtil.generateToken(request.getUsername());
        return authService.loginSuccess(token);
    }

}
