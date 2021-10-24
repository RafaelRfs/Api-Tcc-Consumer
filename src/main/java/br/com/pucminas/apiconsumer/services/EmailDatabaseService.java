package br.com.pucminas.apiconsumer.services;

import br.com.pucminas.apiconsumer.dtos.EmailDto;
import br.com.pucminas.apiconsumer.entities.EmailStatus;
import br.com.pucminas.apiconsumer.enums.StatusEmail;
import br.com.pucminas.apiconsumer.mappers.EmailMapper;
import br.com.pucminas.apiconsumer.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class EmailDatabaseService {

    private final EmailRepository emailRepository;

    public List<EmailDto> findEmailsByIdRequest(String idRequisicao) {
        try {
            return emailRepository.findByIdRequisicao(idRequisicao)
                    .stream()
                    .map(email -> EmailMapper.mapToEmailDto(email))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Erro ao listar os emails: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<EmailStatus> findEmailsbyStatusDb(StatusEmail status){
        return emailRepository.findByStatus(status);
    }

    public List<EmailDto> findEmailsByStatus(StatusEmail status) {
        try {
            return findEmailsbyStatusDb(status)
                    .stream()
                    .map(email -> EmailMapper.mapToEmailDto(email))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Erro ao listar os emails: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public EmailStatus saveEmailDb(EmailDto emailDto) {
        return emailRepository.save(
                EmailMapper.mapToEmailStatus(emailDto)
        );
    }

    public void saveEmailDb(EmailStatus emailStatus) {
        emailRepository.save(
                emailStatus
        );
    }

}
