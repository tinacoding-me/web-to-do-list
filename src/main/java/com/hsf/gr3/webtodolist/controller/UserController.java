package com.hsf.gr3.webtodolist.controller;

import com.hsf.gr3.webtodolist.entity.User;
import com.hsf.gr3.webtodolist.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Hiển thị hồ sơ cá nhân
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", currentUser); // dùng "user" để khớp với profile.html
        return "profile"; // file trực tiếp trong templates/profile.html
    }

    // Cập nhật thông tin cá nhân
    @PostMapping("/update")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                                HttpSession session) {

        User current = (User) session.getAttribute("currentUser");
        if (current == null) {
            return "redirect:/login";
        }

        current.setName(name);

        // Xử lý avatar nếu có upload
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String uploadDir = "uploads/avatars/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filename = System.currentTimeMillis() + "_" + avatarFile.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            try {
                avatarFile.transferTo(dest);
                current.setAvatar("/" + uploadDir + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        userService.updateUser(current);
        session.setAttribute("currentUser", current);

        return "redirect:/user/profile";
    }

    // Đổi mật khẩu
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPass,
                                 @RequestParam("newPassword") String newPass,
                                 @RequestParam("confirmNewPassword") String confirmNewPass,
                                 HttpSession session,
                                 Model model) {

        User current = (User) session.getAttribute("currentUser");
        if (current == null) {
            return "redirect:/login";
        }

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(oldPass, current.getPassword())) {
            model.addAttribute("user", current);
            model.addAttribute("passError", "Mật khẩu cũ không đúng");
            return "profile"; // trả về trực tiếp profile.html
        }

        // Kiểm tra xác nhận mật khẩu mới
        if (!newPass.equals(confirmNewPass)) {
            model.addAttribute("user", current);
            model.addAttribute("passError", "Xác nhận mật khẩu không khớp");
            return "profile";
        }

        // Cập nhật mật khẩu mới
        current.setPassword(passwordEncoder.encode(newPass));
        userService.updateUser(current);
        session.setAttribute("currentUser", current);

        model.addAttribute("user", current);
        model.addAttribute("passSuccess", "Đổi mật khẩu thành công");
        return "profile";
    }
}
