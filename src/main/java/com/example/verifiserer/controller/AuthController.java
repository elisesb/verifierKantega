package com.example.verifiserer.controller;


import com.example.verifiserer.dto.UserDTO;
import com.example.verifiserer.service.AuthService;
import com.example.verifiserer.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        List<String> roles = userDTO.getRoles();

        User user = new User(username, password, roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        try {
            // Forsøk å generere token
            String token = jwtService.generateToken(username, roles);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            // Hvis noe går galt, send feilmelding
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Feil ved generering av token: " + e.getMessage());
        }
    }
}
/*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        List<String> roles = userDTO.getRoles();

        User user = new User(username, password, roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        return ResponseEntity.ok().body("Brukeren er logget inn!");
    }*/


  /*@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            String result = authService.verify(user);
            return ResponseEntity.ok(result);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }*/

