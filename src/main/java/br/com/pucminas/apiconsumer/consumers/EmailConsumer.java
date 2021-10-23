package br.com.pucminas.apiconsumer.consumers;

import br.com.pucminas.apiconsumer.constants.ApiConstants;
import br.com.pucminas.apiconsumer.dtos.EmailDto;
import br.com.pucminas.apiconsumer.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Slf4j
@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = ApiConstants.RABBIT_QUEUE_NAME)
    public void listen(@Payload EmailDto emailDto){
        configuraUUID(emailDto);
        emailService.sendEmailAsync(emailDto);
    }

    private void configuraUUID(EmailDto emailDto) {
        emailDto.setUuid(
                emailDto.getUuid() == null || emailDto.getUuid().trim().isEmpty()?
                        UUID.randomUUID().toString():
                        emailDto.getUuid()
        );
    }

}
