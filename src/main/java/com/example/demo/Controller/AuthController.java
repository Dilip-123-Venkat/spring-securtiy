package com.example.demo.Controller;

//abc
// o baby yes
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

//    @Autowired
//    public AuthController(UserRepository userRepository, UserService userService) {
//        this.userRepository = userRepository;
//        this.userService = userService;
//    }

    // Signup Endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if (opUsername.isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }

        Optional<User> opEmailId = userRepository.findByEmailId(user.getEmailId());
        if (opEmailId.isPresent()) {
            return new ResponseEntity<>("Email ID already exists", HttpStatus.CONFLICT);
        }
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashpw);

        userRepository.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/usersign")
    public ResponseEntity<?> userSignIn(@RequestBody LoginDto dto) {
        String jwtToken = userService.verifyLogin(dto);
        if (jwtToken != null) {

            JWTTokenDto tokenDto = new JWTTokenDto();
            tokenDto.setToken(jwtToken);
            tokenDto.setTokenType("JWT");
            return new ResponseEntity<>(tokenDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Invalid",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
