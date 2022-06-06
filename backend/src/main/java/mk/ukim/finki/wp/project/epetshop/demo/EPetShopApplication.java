package mk.ukim.finki.wp.project.epetshop.demo;

import mk.ukim.finki.wp.project.epetshop.demo.web.MemberRestController;
import mk.ukim.finki.wp.project.epetshop.demo.web.OrderRestController;
import mk.ukim.finki.wp.project.epetshop.demo.web.ProductRestController;
import mk.ukim.finki.wp.project.epetshop.demo.web.TypeRestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@SpringBootApplication
public class EPetShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(EPetShopApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
