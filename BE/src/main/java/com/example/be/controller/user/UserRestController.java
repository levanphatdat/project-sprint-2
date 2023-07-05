package com.example.be.controller.user;

import com.example.be.DTO.response.ResponseMessage;
import com.example.be.DTO.user.RoleDTO;
import com.example.be.DTO.user.UserDTO;
import com.example.be.entity.user.Role;
import com.example.be.entity.user.User;
import com.example.be.security.JwtAuthenticationFilter;
import com.example.be.security.JwtTokenProvider;
import com.example.be.service.user.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserRestController {
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @GetMapping("/detail")
    public ResponseEntity<?> getDetail (HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwt(request);
        if(token!=null &&jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUserNameFromToken(token);
            if(Boolean.FALSE.equals(userService.existsByUsername(username))){
                return new ResponseEntity<>(new ResponseMessage("Tên người dùng không tồn tại")
                        , HttpStatus.BAD_REQUEST);
            }
            UserDTO userDTO = new UserDTO();
            Optional<User> user = userService.findByUsername(username);
            Set<Role> roleSet = user.get().getRoles();
            Set<RoleDTO> roleDTOSet= new HashSet<>();
            RoleDTO roleDTO;
            for( Role role : roleSet) {
                roleDTO = new RoleDTO();
                BeanUtils.copyProperties(role,roleDTO);
                roleDTOSet.add(roleDTO);
            }
            BeanUtils.copyProperties(user.get(), userDTO);
            userDTO.setRoleDTOS(roleDTOSet);
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseMessage("JWT không tồn tại"),HttpStatus.BAD_REQUEST);
        }
    }
}