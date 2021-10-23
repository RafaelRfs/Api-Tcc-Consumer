package br.com.pucminas.apiconsumer.services;

import br.com.pucminas.apiconsumer.dtos.EmailDto;
import br.com.pucminas.apiconsumer.entities.EmailStatus;
import br.com.pucminas.apiconsumer.enums.StatusEmail;
import br.com.pucminas.apiconsumer.mappers.EmailMapper;
import br.com.pucminas.apiconsumer.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    @Transactional
    public List<EmailDto> findEmailsByIdRequest(String idRequisicao){
        try{
            return emailRepository.findByIdRequisicao(idRequisicao)
                    .stream()
                    .map(email -> EmailMapper.mapToEmailDto(email))
                    .collect(Collectors.toList());

        } catch (Exception e){
            log.error("Erro ao listar os emails: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<EmailDto> findEmailsByStatus(StatusEmail status){
        try{
            return emailRepository.findByStatus(status)
                    .stream()
                    .map(email -> EmailMapper.mapToEmailDto(email))
                    .collect(Collectors.toList());

        } catch (Exception e){
            log.error("Erro ao listar os emails: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Async
    public void sendEmailAsync(EmailDto emailDto) {
        log.info("Iniciando envio de email para o payload do ID: {}", emailDto.getUuid());
        emailDto.getEmails().forEach(emailTo -> sendEmail(emailDto, emailTo));
        log.info("Termino do envio dos emails para o payload do ID: {}", emailDto.getUuid());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public EmailStatus saveEmailDb(EmailDto emailDto) {
        return emailRepository.save(
                EmailMapper.mapToEmailStatus(emailDto)
        );
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void saveEmailDb(EmailStatus emailStatus) {
         emailRepository.save(
                emailStatus
        );
    }


    private void sendEmail(EmailDto emailDto, String emailTo) {
        log.info("Enviando email para: {}", emailTo);
        EmailStatus emailStatus =  null;
        try {
            emailDto.setPara(emailTo);
            emailDto.setStatus(StatusEmail.PROCESSANDO);
            emailStatus = saveEmailDb(emailDto);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailDto.getDe());
            message.setTo(emailTo);
            message.setSubject(emailDto.getAssunto());
            message.setText(emailDto.getCorpo());
            emailSender.send(message);
            emailDto.setStatus(StatusEmail.ENVIADO);
            emailStatus.setStatus(emailDto.getStatus());
            saveEmailDb(emailStatus);
            log.info("Email enviado com sucesso para: {}", emailTo);
        } catch (Exception e) {
            emailDto.setStatus(StatusEmail.ERRO);
            emailStatus.setStatus(emailDto.getStatus());
            saveEmailDb(emailStatus);

            log.error("Erro ao enviar o email {} para o UUID {} : {}",
                    emailTo,
                    emailDto.getUuid(),
                    e.getMessage()
            );
        }
    }

}
