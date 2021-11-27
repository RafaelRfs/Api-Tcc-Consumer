package br.com.pucminas.apiconsumer.dtos;

import br.com.pucminas.apiconsumer.enums.StatusEmail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class EmailDto {
    private String uuid;
    private String de;
    private String para;
    private String assunto;
    private StatusEmail status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> emails;
    private String corpo;
    private Long projeto;
}
