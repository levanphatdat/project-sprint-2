package com.example.be.DTO.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SignInForm {
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 5,max = 20, message = "Tên đăng nhập ít nhất 5 ký tự và nhiều nhất 20 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",message = "Tên đăng nhập không được chứa ký tự đặc biệt")
    private String username;
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 5,max = 20, message = "Mật khẩu ít nhất 5 ký tự và nhiều nhất 20 ký tự")
    private String password;

    public SignInForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}