package org.yawdenisk.woodlit.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yawdenisk.woodlit.Entites.User;
import org.yawdenisk.woodlit.Services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
}
