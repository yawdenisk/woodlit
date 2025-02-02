package org.yawdenisk.woodlit.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.yawdenisk.woodlit.Entites.UserRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${keycloak.tocken.url}")
    private String url;
    @Value("${keycloak.client.id}")
    private String clientId;
    @Value("${keycloak.client.secret}")
    private String clientSecret;
    @Autowired
    private RestTemplate restTemplate;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        try {
            // Используем LinkedMultiValueMap для хранения параметров формы
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret); // Укажите секрет клиента
            params.add("username", userRequest.getEmail()); // Используйте email как username
            params.add("password", userRequest.getPassword());
            params.add("grant_type", "password");
            System.out.println(params);

            // Установка заголовков
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Подготовка запроса
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

            // Отправка запроса на Keycloak
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            // Проверка успешности получения токена
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                // Предположим, что responseBody это JSON строка
                // Можно использовать библиотеку, например, Jackson для парсинга JSON
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

                // Извлекаем access_token из ответа
                String accessToken = (String) responseMap.get("access_token");
                return ResponseEntity.ok().body(accessToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
