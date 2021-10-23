package br.com.pucminas.apiconsumer.repositories;

import br.com.pucminas.apiconsumer.entities.EmailStatus;
import br.com.pucminas.apiconsumer.enums.StatusEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailStatus, UUID> {

    List<EmailStatus> findByIdRequisicao(String idRequisicao);

    List<EmailStatus> findByStatus(StatusEmail status);

}