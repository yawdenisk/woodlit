package org.yawdenisk.woodlit.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.yawdenisk.woodlit.DTO.UserDetails;
import org.yawdenisk.woodlit.DTO.UserRequest;
import org.yawdenisk.woodlit.Entites.User;
import org.yawdenisk.woodlit.Services.KeycloakService;
import org.yawdenisk.woodlit.Services.UserService;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private KeycloakService keycloakService;
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        try {
            String accessToken = keycloakService.login(userRequest);
            return ResponseEntity.ok().body(accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error logging in");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserRequest userRequest) {
        try {
            keycloakService.createUser(userRequest);
            userService.createUser(userRequest);
            return ResponseEntity.ok().body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        try {
           keycloakService.logout(token);
            return ResponseEntity.ok().body("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error logging out");
        }
    }

    @GetMapping("/getUserDetails")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
        try {
            UserDetails userDetails = keycloakService.getUserDetails(token);
            return ResponseEntity.ok().body(userDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error getting user details" + e.getMessage());
        }
    }
}
