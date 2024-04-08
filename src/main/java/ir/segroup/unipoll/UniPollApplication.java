package ir.segroup.unipoll;

import ir.segroup.unipoll.ws.model.entity.UserEntity;
import ir.segroup.unipoll.ws.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class UniPollApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniPollApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, Environment environment) {
        return args -> {
            Logger logger = Logger.getLogger(this.getClass().getName());
            try {

                UserEntity admin = new UserEntity(environment.getProperty("admin.id"),
                        environment.getProperty("admin.first-name"),
                        environment.getProperty("admin.last-name"),
                        environment.getProperty("admin.username"),
                        new BCryptPasswordEncoder().encode(environment.getProperty("admin.password")),
                        environment.getProperty("admin.role"));

                int mode = userRepository.findByUsername(environment.getProperty("admin.username"))
                        .map(userEntity -> 1)
                        .orElseGet(() -> {
                            userRepository.save(admin);
                            return 2;
                        });
                logger.log(Level.INFO, "ADMIN init by mode {0}", mode);
            } catch (Exception exception) {
                logger.log(Level.WARNING, "CommandLineRunner exception: {0}", exception.getMessage());
            }
        };
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
