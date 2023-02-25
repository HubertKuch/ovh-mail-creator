package com.hubert.ovhmailcreator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hubert.ovhmailcreator.external.OvhApiException;
import com.hubert.ovhmailcreator.models.CreateEmailCredentials;
import com.hubert.ovhmailcreator.ovh.OvhWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OvhMailCreatorApplication {

    public static void main(String[] args) throws JsonProcessingException, OvhApiException {
        SpringApplication.run(OvhMailCreatorApplication.class, args);
    }

}
