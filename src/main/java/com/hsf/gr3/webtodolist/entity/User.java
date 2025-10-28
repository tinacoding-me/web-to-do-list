package com.hsf.gr3.webtodolist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@ToString(exclude = "password")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name",  nullable = false, columnDefinition = "nvarchar(40)")
    @NotBlank(message = "Xin hãy nhập tên !")
    @Size(min = 5, max = 50, message = "Tên chỉ từ 5 đến 50 kí tự.")
    private String name;

    @Column(name = "email",unique = true, nullable = false, length = 100)
    @NotBlank(message = "Xin hãy nhập email!")
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    @NotBlank(message = "Xin hãy nhập mật khẩu!")
    @Size(min = 6, message = "Mật khẩu từ 6 kí tự trở lên.")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) //Nếu xóa user -> xóa luôn task của user đó
    private List<Task> tasks;

    public User(String email, String name, String password, String avatar) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
    }
}
