package com.hsf.gr3.webtodolist.repository;

import com.hsf.gr3.webtodolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
