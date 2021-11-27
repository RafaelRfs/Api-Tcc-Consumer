package br.com.pucminas.apiconsumer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilderService {

    private final TemplateEngine templateEngine;

    public String build(String message, Long projeto) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("projeto", projeto);
        return templateEngine.process("mailTemplate", context);
    }

}