package org.yawdenisk.woodlit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.yawdenisk.woodlit.Controllers.UserController;
import org.yawdenisk.woodlit.Services.UserService;
import org.yawdenisk.woodlit.entity.User;

@SpringBootApplication
public class WoodlitApplication {
    public static void main(String[] args) {
        SpringApplication.run(WoodlitApplication.class, args);
    }
}
