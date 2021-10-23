package br.com.pucminas.apiconsumer.entities;
import br.com.pucminas.apiconsumer.enums.StatusEmail;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_email_status")
public class EmailStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "from_email")
    private String de;

    @Column(name = "to_email")
    private String para;

    @Column(name = "subject")
    private String assunto;

    @Column(name = "request_id")
    private String idRequisicao;

    @Column(name = "request_body", columnDefinition = "TEXT")
    private String corpo;

    @Column(name = "date_created")
    private LocalDateTime dataEnvio;

    @Column(name = "status")
    private StatusEmail status;
}