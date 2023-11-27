package br.com.ist.job;

import br.com.ist.job.security.dto.JwtAuthResponseDTO;
import br.com.ist.job.security.dto.SignUpRequestDTO;
import br.com.ist.job.security.dto.SigninRequestDTO;
import br.com.ist.job.security.model.User;
import br.com.ist.job.security.service.AuthService;
import br.com.ist.job.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Value("${istjob.default.password}")
    private String defaultPassword;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run(
            AuthService authService,
            UserService userService) {
        return args -> {

            try {
                // Creating istjob@istjob.com user
                var istJobUser = SignUpRequestDTO.builder()
                        .email("istjob@istjob.com")
                        .firstName("Ist")
                        .lastName("Job")
                        .password(defaultPassword)
                        .build();
                log.info("Creating IstJob user: {}", istJobUser);
                authService.signup(istJobUser);
            } catch (Exception e) {
                log.error("Error: {}", e.getMessage());
            }
            
            log.info("Seaching users...");
            
            User istJob = userService.findByEmail("istjob@istjob.com");

            SigninRequestDTO signinRequestAdmin = SigninRequestDTO.convert(istJob, defaultPassword);
            JwtAuthResponseDTO tokenAdmin = authService.signin(signinRequestAdmin);
            log.info("istjob@istjob.com token : {}", tokenAdmin.getToken());
        };
    }
}
