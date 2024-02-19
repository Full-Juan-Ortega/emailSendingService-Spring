package com.send.email.controllers;

import com.send.email.services.models.CorreoRequest;
import com.send.email.services.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class EmailController {

    @Autowired
    IEmailService emailService;

    @PostMapping("/enviar-correo")
    public ResponseEntity<String> enviarCorreo(@RequestBody CorreoRequest correoRequest) {
        //setear data binding
        try {
            emailService.sendEmail(correoRequest);
            return new ResponseEntity<>("Correo enviado exitosamente.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al enviar el correo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hola")
    public ResponseEntity<String> enviarCorreo() {

         return new ResponseEntity<>("Correo enviado exitosamente.", HttpStatus.OK);

    }

    @PostMapping("/recoveryData")
    public ResponseEntity<String> recoveryData() {
        //setear data binding
        try {
            emailService.recoveryAccountData();
            return new ResponseEntity<>("Correo enviado exitosamente.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al enviar el correo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/confirmationEmail")
    public ResponseEntity<String> confirmationEmail() {
        //setear data binding
        try {
            emailService.confirmationEmail();
            return new ResponseEntity<>("Correo enviado exitosamente.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al enviar el correo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}