package com.javaproject.quizapp.services;

import com.javaproject.quizapp.models.Admin;
import com.javaproject.quizapp.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<String> createAdmin(Admin admin) {
        try {
            String encodedPassword = bCryptPasswordEncoder.encode(admin.getPassword());
            admin.setPassword(encodedPassword);
            adminRepository.save(admin);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> authenticateAdmin(Admin adminToBeVerified) {
        try {
            Admin admin = adminRepository.findByUsername(adminToBeVerified.getUsername());
            if (admin != null) {
                String passwordToBeVerified = adminToBeVerified.getPassword();
                if (bCryptPasswordEncoder.matches(passwordToBeVerified, admin.getPassword())) {
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
