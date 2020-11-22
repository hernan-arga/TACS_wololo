package tacs.wololo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class WololoApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(WololoApplication.class, args);
    }


}
