package com.send.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

    @Value("${email.username}")
    private String email /*= "nocountryc1529@gmail.com"*/;

    @Value("${email.password}")
    private String password /*= "yogbzwcgpgvgxrce"*/;

    private Properties getMailProperties() {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");//auth required before send email
        properties.put("mail.smtp.starttls.enable", "true");//tls is a secured channel to send email
        properties.put("mail.smtp.host", "smtp.gmail.com"); // email server host
        properties.put("mail.smtp.port", "587"); // Ejemplo: 587 // 597 is secured common port to sending email
        return properties;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(getMailProperties());
        mailSender.setUsername(email);
        mailSender.setPassword(password);
        return mailSender;
    }

    @Bean
    public ResourceLoader resourceLoader() {
        return new DefaultResourceLoader();
    }
}
