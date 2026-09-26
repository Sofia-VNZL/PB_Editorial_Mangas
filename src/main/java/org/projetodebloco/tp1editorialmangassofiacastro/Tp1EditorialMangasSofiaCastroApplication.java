package org.projetodebloco.tp1editorialmangassofiacastro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class Tp1EditorialMangasSofiaCastroApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tp1EditorialMangasSofiaCastroApplication.class, args);
    }

}
