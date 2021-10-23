package br.com.pucminas.apiconsumer.mappers;

import br.com.pucminas.apiconsumer.dtos.EmailDto;
import br.com.pucminas.apiconsumer.entities.EmailStatus;
import java.time.LocalDateTime;

public class EmailMapper {

    public static EmailStatus mapToEmailStatus(EmailDto emailDto){
        EmailStatus emailStatus = new EmailStatus();
        emailStatus.setAssunto(emailDto.getAssunto());
        emailStatus.setStatus(emailDto.getStatus());
        emailStatus.setDataEnvio(LocalDateTime.now());
        emailStatus.setIdRequisicao(emailDto.getUuid());
        emailStatus.setDe(emailDto.getDe());
        emailStatus.setPara(emailDto.getPara());
        emailStatus.setCorpo(emailDto.getCorpo());
        return emailStatus;
    }

    public static EmailDto mapToEmailDto(EmailStatus emailStatus){
        EmailDto emailDto = new EmailDto();
        emailDto.setUuid(emailStatus.getIdRequisicao());
        emailDto.setAssunto(emailStatus.getAssunto());
        emailDto.setDe(emailStatus.getDe());
        emailDto.setPara(emailStatus.getPara());
        emailDto.setStatus(emailStatus.getStatus());
        emailDto.setCorpo(emailStatus.getCorpo());
        return emailDto;
    }

}
