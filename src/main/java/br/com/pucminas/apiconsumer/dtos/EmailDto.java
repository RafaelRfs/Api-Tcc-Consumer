package br.com.pucminas.apiconsumer.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class EmailDto {
    private String uuid;
    private String assunto;
    private String titulo;
    private List<String> cc;
    private List<String> emails;
    private String corpo;
}
