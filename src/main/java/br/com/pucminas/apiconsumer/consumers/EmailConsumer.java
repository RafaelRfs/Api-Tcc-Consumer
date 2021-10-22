package br.com.pucminas.apiconsumer.consumers;

import br.com.pucminas.apiconsumer.constants.ApiConstants;
import br.com.pucminas.apiconsumer.dtos.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailConsumer {

    @RabbitListener(queues = ApiConstants.RABBIT_QUEUE_NAME)
    public void listen(@Payload EmailDto emailDto){
        log.info("Do anything ");

    }

}
