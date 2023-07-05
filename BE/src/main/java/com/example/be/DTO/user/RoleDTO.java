package com.example.be.DTO.user;

import com.example.be.entity.user.RoleName;

import java.util.HashSet;
import java.util.Set;

public class RoleDTO {
    private Integer id;
    private RoleName name;
    Set<UserDetailDTO> userDetailDTOSet = new HashSet<>();
    public RoleDTO() {
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public RoleName getName() {
        return name;
    }
    public void setName(RoleName name) {
        this.name = name;
    }
    public Set<UserDetailDTO> getUserDetailDTOSet() {
        return userDetailDTOSet;
    }
    public void setUserDetailDTOSet(Set<UserDetailDTO> userDetailDTOSet) {
        this.userDetailDTOSet = userDetailDTOSet;
    }
}