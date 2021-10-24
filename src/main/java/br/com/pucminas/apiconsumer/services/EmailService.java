package br.com.pucminas.apiconsumer.services;

import br.com.pucminas.apiconsumer.dtos.EmailDto;
import br.com.pucminas.apiconsumer.entities.EmailStatus;
import br.com.pucminas.apiconsumer.enums.StatusEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailDatabaseService emailDatabaseService;
    private final MailContentBuilderService mailContentBuilderService;
    private final JavaMailSender emailSender;

    @Async
    public void sendEmailAsync(EmailDto emailDto) {
        log.info("Iniciando envio de email para o payload do ID: {}", emailDto.getUuid());
        emailDto.getEmails().forEach(emailTo -> sendEmail(emailDto, emailTo));
        log.info("Termino do envio dos emails para o payload do ID: {}", emailDto.getUuid());
    }

    private MimeMessagePreparator prepareEmail(EmailDto emailDto) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailDto.getDe());
            messageHelper.setTo(emailDto.getPara());
            messageHelper.setSubject(emailDto.getAssunto());
            messageHelper.setText(
                    mailContentBuilderService.build(emailDto.getCorpo()),
                    true
            );
        };
    }

    private void sendEmail(EmailDto emailDto, String emailTo) {
        log.info("Enviando email para: {}", emailTo);
        EmailStatus emailStatus = null;
        try {
            emailDto.setPara(emailTo);
            emailDto.setStatus(StatusEmail.PROCESSANDO);
            emailStatus = emailDatabaseService.saveEmailDb(emailDto);
            emailSender.send(prepareEmail(emailDto));
            emailDto.setStatus(StatusEmail.ENVIADO);
            emailStatus.setStatus(emailDto.getStatus());
            emailDatabaseService.saveEmailDb(emailStatus);
            log.info("Email enviado com sucesso para: {}", emailTo);
        } catch (Exception e) {
            emailDto.setStatus(StatusEmail.ERRO);
            emailStatus.setStatus(emailDto.getStatus());
            emailDatabaseService.saveEmailDb(emailStatus);
            log.error("Erro ao enviar o email {} para o UUID {} : {}",
                    emailTo,
                    emailDto.getUuid(),
                    e.getMessage()
            );
        }
    }

}
