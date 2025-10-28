package com.hsf.gr3.webtodolist.repository;

import com.hsf.gr3.webtodolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
