package com.example.be.controller.authencication;

import com.example.be.DTO.request.ChangePasswordRequest;
import com.example.be.DTO.request.RegisterForm;
import com.example.be.DTO.request.ResetPasswordRequest;
import com.example.be.DTO.request.SignInForm;
import com.example.be.DTO.response.ResponseMessage;
import com.example.be.DTO.user.UserOtpDTO;
import com.example.be.entity.user.Role;
import com.example.be.entity.user.User;
import com.example.be.security.JwtTokenProvider;
import com.example.be.security.UserPrinciple;
import com.example.be.service.mail.IEmailService;
import com.example.be.service.user.IRoleService;
import com.example.be.service.user.IUserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")

public class AuthRestController {
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private IEmailService iEmailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody SignInForm signInForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> map = new LinkedHashMap<>();
            List<FieldError> err = bindingResult.getFieldErrors();
            for (FieldError error : err) {
                if (!map.containsKey(error.getField())) {
                    map.put(error.getField(), error.getDefaultMessage());
                }
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } else if (Boolean.FALSE.equals(userService.existsByUsername(signInForm.getUsername()))) {
            return new ResponseEntity<>(new ResponseMessage("Tên người dùng không tồn tại"), HttpStatus.BAD_REQUEST);
        } else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.createToken(authentication);
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            return ResponseEntity.ok(new com.example.api.dto.response.JwtResponse(token, userPrinciple.getUsername(),
                    userPrinciple.getAuthorities(),userPrinciple.getName()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterForm registerForm){

        if(userService.existsByUsername(registerForm.getUsername())){
            return new ResponseEntity<>("Tên đăng ký đã tồn tại!.", HttpStatus.BAD_REQUEST);
        }

        if(userService.existsByEmail(registerForm.getEmail())){
            return new ResponseEntity<>("Email đã tồn tại!.", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(registerForm.getName());
        user.setUserName(registerForm.getUsername());
        user.setEmail(registerForm.getEmail());
        user.setGender(registerForm.isGender());
        user.setAddress(registerForm.getAddress());
        user.setDateOfBirth(registerForm.getDateOfBirth());
        user.setPhoneNumber(registerForm.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>("Đăng ký thành công!.", HttpStatus.CREATED);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @Validated @RequestBody
    ChangePasswordRequest changePasswordRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> map = new LinkedHashMap<>();
            List<FieldError> err = bindingResult.getFieldErrors();
            for (FieldError error : err) {
                if (!map.containsKey(error.getField())) {
                    map.put(error.getField(), error.getDefaultMessage());
                }
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        final String requestTokenHeader = request.getHeader("Authorization");
        String username;
        String jwtToken;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenProvider.getUserNameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                throw new JwtException("Mã thông báo JWT đã hết hạn", e);
            }
        } else {
            return new ResponseEntity<>(new ResponseMessage("Mã JWT không chính xác"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Tên người dùng không tồn tại"));
        if (Boolean.FALSE.equals(userService.checkIfValidOldPassword(user, changePasswordRequest.getOldPassword()))) {
            return new ResponseEntity<>(new ResponseMessage("Mật khẩu hiện tại không đúng"), HttpStatus.BAD_REQUEST);
        }
        if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getOldPassword())) {
            return new ResponseEntity<>(new ResponseMessage("Mật khẩu mới không được trùng với mật khẩu cũ"), HttpStatus.BAD_REQUEST);
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            return new ResponseEntity<>(new ResponseMessage("Mật khẩu xác nhận không trùng khớp"), HttpStatus.BAD_REQUEST);
        }
        userService.changeUserPassword(user, changePasswordRequest.getNewPassword());
        return ResponseEntity.ok(new ResponseMessage("Đổi mật khẩu thành công!"));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Validated @RequestBody ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            User user = userService.findByEmailEmployee(resetPasswordRequest.getEmail());
            if(user != null){
                if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
                    return new ResponseEntity<>(new ResponseMessage("Mật khẩu xác nhận không trùng khớp"), HttpStatus.BAD_REQUEST);
                } else {
                    userService.changeUserPassword(user, resetPasswordRequest.getNewPassword());
                    return ResponseEntity.ok(new ResponseMessage("Đổi mật khẩu thành công!"));
                }
            }else {
                return new ResponseEntity<>(new ResponseMessage("Không tìm thấy email"), HttpStatus.BAD_REQUEST);
            }
        } else {
            Map<String, String> map = new LinkedHashMap<>();
            List<FieldError> err = bindingResult.getFieldErrors();
            for (FieldError error : err) {
                if (!map.containsKey(error.getField())) {
                    map.put(error.getField(), error.getDefaultMessage());
                }
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }
}