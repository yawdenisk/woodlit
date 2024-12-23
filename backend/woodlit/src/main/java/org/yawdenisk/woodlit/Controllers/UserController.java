package org.yawdenisk.woodlit.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yawdenisk.woodlit.Entites.User;
import org.yawdenisk.woodlit.Mappers.UserMapper;
import org.yawdenisk.woodlit.Services.UserService;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestParam("name") String name,
                                        @RequestParam("last_name") String last_name,
                                        @RequestParam("email") String email,
                                        @RequestParam("password") String password,
                                        @RequestParam("country") String country,
                                        @RequestParam("city") String city,
                                        @RequestParam("post_index") String post_index,
                                        @RequestParam("adress") String adress,
                                        @RequestParam("username") String username){
        try{
            User user = new User();
            user.setName(name);
            user.setLast_name(last_name);
            user.setCity(city);
            user.setAddress(adress);
            user.setEmail(email);
            user.setPost_index(post_index);
            user.setUsername(username);
            user.setCountry(country);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles("USER");
            userService.createUser(user);
            return ResponseEntity.ok("User created");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("User don't created");
        }
    }
    @GetMapping("/getUserDetails")
    public ResponseEntity<User> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/checkAuth")
    public ResponseEntity<Boolean> checkAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");
        return ResponseEntity.ok(isAuthenticated);
    }
    @GetMapping("/getCsrf")
    public ResponseEntity<String> getCsrf(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return ResponseEntity.ok(csrfToken.getToken());
    }
}
