package org.yawdenisk.woodlit.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yawdenisk.woodlit.Entites.User;
import org.yawdenisk.woodlit.Entites.UserResponce;
import org.yawdenisk.woodlit.Mappers.UserMapper;
import org.yawdenisk.woodlit.Services.UserService;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> userRequest){
        try{
            User user = new User();
            user.setName(userRequest.get("name"));
            user.setLast_name(userRequest.get("last_name"));
            user.setCity(userRequest.get("city"));
            user.setAddress(userRequest.get("address"));
            user.setEmail(userRequest.get("email"));
            user.setPost_index(userRequest.get("post_index"));
            user.setUsername(userRequest.get("username"));
            user.setCountry(userRequest.get("country"));
            user.setPassword(passwordEncoder.encode(userRequest.get("password")));
            user.setRoles("ROLE_USER");
            userService.createUser(user);
            return ResponseEntity.ok("User created");
        } catch (Exception e) {
            if(e.getMessage().contains("user.email")){
                return ResponseEntity.badRequest().body("This email already exists");
            }
            if(e.getMessage().contains("user.username")){
                return ResponseEntity.badRequest().body("This username already exists");
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getUserDetails")
    public ResponseEntity<UserResponce> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.findByUsername(authentication.getName());
            UserResponce userResponce = userMapper.mapToUserResponce(user);
            return ResponseEntity.ok(userResponce);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest, HttpServletRequest request) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        HttpSession session = request.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Login successful");
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }
}
