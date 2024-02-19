# ENVIAR CORREOS CON JAVA MAIL

![Demo](https://github.com/DanielEspanadero/java-mail/blob/main/docs/javamail.jpg)

Este proyecto es una aplicación Java que permite enviar correos electrónicos utilizando el servidor SMTP de Gmail. Está implementado como una aplicación Spring Boot con los siguientes componentes clave:

1. **EmailConfig:** Esta clase de configuración establece las propiedades necesarias para conectarse al servidor SMTP de Gmail y enviar correos electrónicos.

2. **EmailController:** Esta clase de controlador expone un endpoint REST para enviar correos electrónicos. Utiliza la interfaz IEmailService para delegar la funcionalidad real de envío de correos.

3. **CorreoRequest:** Esta es una simple clase POJO (Plain Old Java Object) que representa la solicitud de correo electrónico, incluyendo la dirección de correo electrónico del destinatario y el asunto del correo.

4. **IEmailService:** Esta es una interfaz que define el contrato para el servicio de correo electrónico. La clase EmailServiceImpl implementa esta interfaz para manejar la lógica de envío de correos.

5. **EmailServiceImpl:** Esta clase implementa la interfaz IEmailService y es responsable de enviar correos utilizando el servidor SMTP de Gmail.

## Configuración

La clase EmailConfig se utiliza para configurar las propiedades necesarias para enviar correos. Lee las propiedades requeridas desde el archivo email.properties y configura el bean JavaMailSender.

```agsl
@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

    @Value("${email.username}")
    private String email;

    @Value("${email.password}")
    private String password;

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Ejemplo: smtp.gmail.com
        properties.put("mail.smtp.port", "587"); // Ejemplo: 587
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
```

## Envío de Correos Electrónicos

La funcionalidad de envío de correos electrónicos se implementa en la clase EmailServiceImpl, que implementa la interfaz IEmailService.

## CorreoRequest

La clase CorreoRequest representa la solicitud de correo electrónico que contiene la dirección de correo electrónico del destinatario y el asunto del correo.

```agsl
public class CorreoRequest {
    private String destinatario;
    private String asunto;

    public CorreoRequest() {
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
}

```

## Envío del Correo Electrónico

El método enviarCorreo en EmailServiceImpl es responsable de enviar el correo electrónico. Carga el contenido del correo desde una plantilla HTML y lo envía como un correo HTML.

```agsl
@Service
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private String cargarContenidoCorreo() throws IOException {
        File file = new ClassPathResource("templates/email.html").getFile();
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }

    @Override
    public void enviarCorreo(CorreoRequest correoRequest) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(correoRequest.getDestinatario());
            helper.setSubject(correoRequest.getAsunto());

            String contenidoHtml = cargarContenidoCorreo();

            helper.setText(contenidoHtml, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}
```

## Plantilla HTML

El contenido del correo electrónico se carga desde la plantilla email.html ubicada en la carpeta templates.

```agsl
    private String cargarContenidoCorreo() throws IOException {
        File file = new ClassPathResource("templates/email.html").getFile();
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }
```

Es importante tener en cuenta que la plantilla email.html debe estar diseñada adecuadamente con el contenido necesario y marcadores de posición que se reemplazarán dinámicamente con valores reales.

## API REST

La clase EmailController expone un endpoint REST en /enviar_correo para enviar correos electrónicos.

```agsl
@RestController
@RequestMapping
public class EmailController {

    @Autowired
    IEmailService emailService;

    @PostMapping("/enviar_correo")
    public ResponseEntity<String> enviarCorreo(@RequestBody CorreoRequest correoRequest) {
        try {
            emailService.enviarCorreo(correoRequest);
            return new ResponseEntity<>("Correo enviado exitosamente.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al enviar el correo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
```

Para enviar un correo electrónico, se debe hacer una solicitud POST a /enviar_correo, proporcionando la dirección de correo electrónico del destinatario y el asunto del correo en el cuerpo de la solicitud como JSON.

## Autor ✒️

* [Daniel Españadero](https://github.com/DanielEspanadero)


## Licencia 📄

_Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENCIA](https://github.com/DanielEspanadero/java-mail/blob/main/LICENSE) para más detalles._