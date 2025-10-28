package com.hsf.gr3.webtodolist.service;

import com.hsf.gr3.webtodolist.entity.User;

public interface UserService {

    public User getUserByEmail(String email); //

    public User register(User user); //Đăng kí

    public User login(String email, String password); //Đăng nhập
}
