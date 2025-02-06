package org.yawdenisk.woodlit.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.yawdenisk.woodlit.DTO.UserDetails;
import org.yawdenisk.woodlit.DTO.UserRequest;

import java.util.*;

@Service
public class KeycloakService {
    @Value("${keycloak.tocken.url}")
    private String tockenUrl;

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    @Value("${keycloak.registration.url}")
    private String registrationUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String login(UserRequest userRequest) throws JsonProcessingException {
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
        Map<String, String> responseMap = objectMapper.readValue(response, Map.class);
        String accessToken = responseMap.get("access_token");
        return accessToken;
    }

    public void logout(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange("http://localhost:8080/realms/woodlit/protocol/openid-connect/logout", HttpMethod.POST, entity, String.class);
    }

    public UserDetails getUserDetails(String token) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/realms/woodlit/protocol/openid-connect/userinfo", HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        UserDetails userDetails = objectMapper.readValue(response.getBody(), UserDetails.class);
        return userDetails;
    }

    public void createUser(UserRequest userRequest) throws JsonProcessingException {
        UserRequest adminRequest = new UserRequest();
        adminRequest.setEmail("admin@admin.com");
        adminRequest.setPassword("admin");
        String adminToken = login(adminRequest);

        Map<String, Object> keycloakUser = new HashMap<>();
        keycloakUser.put("username", userRequest.getEmail());
        keycloakUser.put("email", userRequest.getEmail());
        keycloakUser.put("firstName", userRequest.getFirstName());
        keycloakUser.put("lastName", userRequest.getLastName());
        keycloakUser.put("enabled", true);

        List<Map<String, Object>> credentials = new ArrayList<>();
        Map<String, Object> password = new HashMap<>();
        password.put("type", "password");
        password.put("value", userRequest.getPassword());
        password.put("temporary", false);
        credentials.add(password);
        keycloakUser.put("credentials", credentials);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(keycloakUser, headers);
        restTemplate.exchange(registrationUrl, HttpMethod.POST, entity, String.class);
    }
}
