package com.ipi.gestiondechampionnatapi;

import com.ipi.gestiondechampionnatapi.models.User;
import com.ipi.gestiondechampionnatapi.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class GestionDeChampionnatApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDeChampionnatApiApplication.class, args);
    }
}
