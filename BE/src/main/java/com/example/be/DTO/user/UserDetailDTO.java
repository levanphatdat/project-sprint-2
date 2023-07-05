package com.example.be.DTO.user;

import java.util.HashSet;
import java.util.Set;

public class UserDetailDTO {
    private Integer id;
    private String code;
    private String name;
    private boolean gender;
    private String dateOfBirth;
    private String address;
    private String email;
    private String phoneNumber;
    private String userName;
    private Set<RoleDTO> roleDTOSetSet = new HashSet<>();

    public UserDetailDTO() {
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Set<RoleDTO> getRoleDTOSetSet() {
        return roleDTOSetSet;
    }
    public void setRoleDTOSetSet(Set<RoleDTO> roleDTOSetSet) {
        this.roleDTOSetSet = roleDTOSetSet;
    }
}