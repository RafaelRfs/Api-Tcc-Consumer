package br.com.pucminas.apiconsumer.services;

import br.com.pucminas.apiconsumer.dtos.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Async
    public void sendEmailAsync(EmailDto emailDto){
        log.info("Iniciando envio de email para o payload do ID: {}", emailDto.getUuid());
        emailDto.getEmails().forEach(emailTo -> sendEmail(emailDto, emailTo));
        log.info("Payload de emails enviados com sucesso para o UUID: {}", emailDto.getUuid());
    }

    private void sendEmail(EmailDto emailDto, String emailTo) {
        log.info("Enviando email para: {}", emailTo);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailTo);
        message.setSubject(emailDto.getAssunto());
        message.setText(emailDto.getCorpo());
        emailSender.send(message);
        log.info("Email enviado com sucesso para: {}", emailTo);
    }


}
