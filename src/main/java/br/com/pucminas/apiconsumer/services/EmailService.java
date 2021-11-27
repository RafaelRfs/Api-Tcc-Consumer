package br.com.pucminas.apiconsumer.services;

import br.com.pucminas.apiconsumer.dtos.EmailDto;
import br.com.pucminas.apiconsumer.entities.EmailStatus;
import br.com.pucminas.apiconsumer.enums.StatusEmail;
import br.com.pucminas.apiconsumer.mappers.EmailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public void reSendEmailAsync(List<EmailStatus> emails){
        emails.forEach(this::sendEmail);
    }

    private MimeMessagePreparator prepareEmail(EmailDto emailDto) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailDto.getDe());
            messageHelper.setTo(emailDto.getPara());
            messageHelper.setSubject(emailDto.getAssunto());
            messageHelper.setText(
                    mailContentBuilderService.build(emailDto.getCorpo(),emailDto.getProjeto()),
                    true
            );
        };
    }


    @Async
    private void sendEmail(EmailStatus email){
        try{
            EmailDto emailDto = EmailMapper.mapToEmailDto(email);
            emailSender.send(prepareEmail(emailDto));
            email.setStatus(StatusEmail.ENVIADO);
            emailDatabaseService.saveEmailDb(email);

        } catch(Exception e){
            log.error("Erro ao reprocessar o email: {}", e.getMessage());
            email.setStatus(StatusEmail.ERRO);
            emailDatabaseService.saveEmailDb(email);
        }
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
