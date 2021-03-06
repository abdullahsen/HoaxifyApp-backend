package com.abdullahsen.ws;

import com.abdullahsen.ws.hoax.Hoax;
import com.abdullahsen.ws.hoax.HoaxService;
import com.abdullahsen.ws.hoax.vm.HoaxSubmitVM;
import com.abdullahsen.ws.user.User;
import com.abdullahsen.ws.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class WsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }

    @Bean
    @Profile("dev")
    CommandLineRunner createInitialUsers(UserService userService, HoaxService hoaxService) {
        return args -> {

            try {
                userService.getByUsername("abdullahsen1");
            }catch (Exception e){
                for (int i = 1; i <= 25; i++) {
                    User user = new User();
                    user.setDisplayName("abdullahsen" + i);
                    user.setUsername("abdullahsen" + i);
                    user.setPassword("Password12");
                    userService.save(user);

                    for (int j = 1; j <= 20; j++) {
                        HoaxSubmitVM hoax = new HoaxSubmitVM();
                        hoax.setContent("Hoax " + j + " from " + user.getDisplayName());
                        hoaxService.save(hoax, user);
                    }
                }
            }



        };
    }
}
