package org.yawdenisk.woodlit.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.yawdenisk.woodlit.Entites.UserRequest;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${keycloak.tocken.url}")
    private String tockenUrl;

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    @Value("${keucloak.registration.url}")
    private String registrationUrl;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("username", userRequest.getEmail());
            params.add("password", userRequest.getPassword());
            params.add("grant_type", "password");
            params.add("scope", "openid");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
            String response = restTemplate.postForObject(tockenUrl, entity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            String accessToken = (String) responseMap.get("access_token");

            return ResponseEntity.ok().body(accessToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error logging in");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserRequest userRequest) {
        try {
            UserRequest adminRequest = new UserRequest();
            adminRequest.setEmail("admin@admin.com");
            adminRequest.setPassword("admin");
            ResponseEntity<?> responseEntity = login(adminRequest);
            String adminToken = (String) responseEntity.getBody();

            Map<String, Object> user = new HashMap<>();
            user.put("username", userRequest.getEmail());
            user.put("email", userRequest.getEmail());
            user.put("firstName", userRequest.getFirstName());
            user.put("lastName", userRequest.getLastName());
            user.put("enabled", true);

            List<Map<String, Object>> credentials = new ArrayList<>();
            Map<String, Object> password = new HashMap<>();
            password.put("type", "password");
            password.put("value", userRequest.getPassword());
            password.put("temporary", false);
            credentials.add(password);
            user.put("credentials", credentials);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + adminToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(user, headers);
            ResponseEntity<String> createResponse = restTemplate.exchange(registrationUrl, HttpMethod.POST, entity, String.class);

            if (createResponse.getStatusCode() == HttpStatus.CREATED) {
                return ResponseEntity.ok().body("User created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user: " + createResponse.getBody());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            restTemplate.exchange("http://localhost:8080/realms/woodlit/protocol/openid-connect/logout",HttpMethod.POST, entity, String.class);
            return ResponseEntity.ok().body("Logged out successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error logging out");
        }
    }
    @GetMapping("/getUserDetails")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/realms/woodlit/protocol/openid-connect/userinfo",HttpMethod.GET, entity, String.class);
            return ResponseEntity.ok().body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error getting user details");
        }
    }
}
