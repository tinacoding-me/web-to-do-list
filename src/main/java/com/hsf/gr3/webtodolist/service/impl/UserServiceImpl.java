package com.hsf.gr3.webtodolist.service.impl;

import com.hsf.gr3.webtodolist.entity.User;
import com.hsf.gr3.webtodolist.repository.UserRepo;
import com.hsf.gr3.webtodolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByEmail(String email) {
        User existingUser = userRepo.findByEmail(email);
        if (existingUser != null) {
            return existingUser;
        }
        return null;
    }

    @Override
    public User register(User user) {
        User existingUser = userRepo.findByEmail(user.getEmail());

        if (existingUser != null) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepo.findByEmail(email);

        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
    return null;
    }

    @Override
    public void updateUser(User user) {
        userRepo.save(user);
    }
}
