package com.javaproject.quizapp.services;

import com.javaproject.quizapp.models.User;
import com.javaproject.quizapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<String> createUser(User user) {
        try {
            if(userRepository.findByUsername(user.getUsername()) != null) {
                return new ResponseEntity<>("Username already exists, please choose some other username", HttpStatus.BAD_REQUEST);
            }
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> authenticateUser(User userToBeVerified) {
        try {
            User user = userRepository.findByUsername(userToBeVerified.getUsername());
            if (user != null) {
                String passwordToBeVerified = userToBeVerified.getPassword();
                if (bCryptPasswordEncoder.matches(passwordToBeVerified, user.getPassword())) {
                    return new ResponseEntity<>("Success", HttpStatus.OK);
                } else return new ResponseEntity<>("Wrong Password", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
    }
}
