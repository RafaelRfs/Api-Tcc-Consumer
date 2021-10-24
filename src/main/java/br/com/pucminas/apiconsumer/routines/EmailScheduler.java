package br.com.pucminas.apiconsumer.routines;

import br.com.pucminas.apiconsumer.enums.StatusEmail;
import br.com.pucminas.apiconsumer.services.EmailDatabaseService;
import br.com.pucminas.apiconsumer.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Calendar;

@Slf4j
@Configuration
@EnableScheduling
public class EmailScheduler {

    @Autowired
    private EmailDatabaseService databaseService;

    @Autowired
    private EmailService emailService;

    @Scheduled(initialDelayString = "${app.schedule.initial_delay_time}", fixedDelayString = "${app.schedule.fixed_delay_time}")
    public void processEmailsNotSended(){
        log.info("Iniciando routina de reprocessamento de emails as {} ", Calendar.getInstance().getTime());
        emailService.reSendEmailAsync(
                databaseService.findEmailsbyStatusDb(StatusEmail.ERRO)
        );
        log.info("Termino da rotina de reprocessamento de email as {}", Calendar.getInstance().getTime());
    }

}
