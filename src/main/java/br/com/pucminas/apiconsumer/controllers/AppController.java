package br.com.pucminas.apiconsumer.controllers;

import br.com.pucminas.apiconsumer.dtos.EmailDto;
import br.com.pucminas.apiconsumer.enums.StatusEmail;
import br.com.pucminas.apiconsumer.services.EmailDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/consumer/emails")
@RequiredArgsConstructor
public class AppController {

    private final EmailDatabaseService emailDatabaseService;

    @GetMapping("/{uuid}")
    public ResponseEntity<List<EmailDto>> findEmailsById(@PathVariable String uuid){
        return new ResponseEntity<>(
                emailDatabaseService.findEmailsByIdRequest(uuid),
                HttpStatus.OK
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmailDto>> findEmailsByStatus(@PathVariable StatusEmail status){
        return new ResponseEntity<>(
                emailDatabaseService.findEmailsByStatus(status),
                HttpStatus.OK
        );
    }

}
