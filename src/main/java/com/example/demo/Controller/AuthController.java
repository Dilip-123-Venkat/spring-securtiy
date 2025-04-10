package com.example.demo.Controller;

import com.example.demo.PayLoad.JWTTokenDto;
import com.example.demo.PayLoad.LoginDto;
import com.example.demo.Repositary.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // ✅ Signup Endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if (opUsername.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        Optional<User> opEmailId = userRepository.findByEmailId(user.getEmailId());
        if (opEmailId.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email ID already exists");
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    // ✅ Login Endpoint
    @PostMapping("/usersign")
    public ResponseEntity<?> userSignIn(@RequestBody LoginDto dto) {
        String jwtToken = userService.verifyLogin(dto);

        if (jwtToken != null) {
            JWTTokenDto tokenDto = new JWTTokenDto();
            tokenDto.setToken(jwtToken);
            tokenDto.setTokenType("Bearer"); // ✅ Use "Bearer" to match Authorization header format

            return ResponseEntity.ok(tokenDto); // ✅ 200 OK is better for login success
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
    @GetMapping("/print")
    public String addCars() {
        return "added";
    }
}
