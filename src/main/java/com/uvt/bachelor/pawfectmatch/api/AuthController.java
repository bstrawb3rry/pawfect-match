package com.uvt.bachelor.pawfectmatch.api;

import com.uvt.bachelor.pawfectmatch.entity.User;
import com.uvt.bachelor.pawfectmatch.model.AuthRequest;
import com.uvt.bachelor.pawfectmatch.model.AuthResponse;
import com.uvt.bachelor.pawfectmatch.model.UserDto;
import com.uvt.bachelor.pawfectmatch.repository.PetOwnerRepository;
import com.uvt.bachelor.pawfectmatch.repository.UserRepository;
import com.uvt.bachelor.pawfectmatch.security.JwtTokenUtil;
import com.uvt.bachelor.pawfectmatch.service.AuthService;
import com.uvt.bachelor.pawfectmatch.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetOwnerRepository ownerRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        Optional<User> optionalUser = userRepository.findByUsername(authRequest.getUsername());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Username does not exist");
        }
        return ResponseEntity.ok(new AuthResponse(jwt, optionalUser.get().getOwner().getId()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        return ResponseEntity.status(HttpStatus.OK).body(authService.signup(userDto));
    }
}
