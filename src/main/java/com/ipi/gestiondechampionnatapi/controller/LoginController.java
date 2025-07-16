// src/main/java/com/ipi/gestiondechampionnatapi/controller/LoginController.java
package com.ipi.gestiondechampionnatapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}