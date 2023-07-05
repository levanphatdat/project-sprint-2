package com.example.be.DTO.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegisterForm {
    private String name;
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 5,max = 20, message = "Tên đăng nhập ít nhất 5 ký tự và nhiều nhất 20 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",message = "Tên đăng nhập không được chứa ký tự đặc biệt")
    private String username;
    private String email;
    @Length(min = 5,max = 20, message = "Mật khẩu ít nhất 5 ký tự và nhiều nhất 20 ký tự")
    @NotBlank(message = "Không được bỏ trống")
    private String password;
    private boolean gender;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;

    public RegisterForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
