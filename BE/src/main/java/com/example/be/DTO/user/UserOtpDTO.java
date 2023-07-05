package com.example.be.DTO.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserOtpDTO {
    private String email;
    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^\\d{6}$",message = "Vui lòng nhập đúng định dạng OTP VD:XXXXXX (X là chữ số)")
    private String code;

    public UserOtpDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}