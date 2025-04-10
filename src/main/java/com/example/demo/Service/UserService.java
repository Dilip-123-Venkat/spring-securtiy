package com.example.demo.Service;

import com.example.demo.PayLoad.LoginDto;
import com.example.demo.Repositary.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    private JWTService jwtService;

    public String verifyLogin(LoginDto dto) {
        Optional<User> opUser = userRepository.findByUsername(dto.getUsername());
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (bCrypt.matches(dto.getPassword(), user.getPassword())) {
                return jwtService.generateToken(user.getUsername());
            }
        }
        return null;
    }
}
