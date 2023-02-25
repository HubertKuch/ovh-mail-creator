package com.hubert.ovhmailcreator.ovh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubert.ovhmailcreator.configuration.OvhConfiguration;
import com.hubert.ovhmailcreator.external.OvhApi;
import com.hubert.ovhmailcreator.external.OvhApiException;
import com.hubert.ovhmailcreator.models.CreateEmailCredentials;
import com.hubert.ovhmailcreator.models.EmailCreatedResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OvhWrapper {
    private final OvhConfiguration ovhConfiguration;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OvhApi ovhApi;

    public OvhWrapper(OvhConfiguration ovhConfiguration) {
        this.ovhConfiguration = ovhConfiguration;
        this.ovhApi = new OvhApi(ovhConfiguration.getEndpoint(),
                                                     ovhConfiguration.getAppKey(),
                                                     ovhConfiguration.getAppSecret(),
                                                     ovhConfiguration.getConsumerKey()
        );

    }


    public EmailCreatedResponse createEmail(CreateEmailCredentials credentials) {
        try {
            String body = objectMapper.writeValueAsString(credentials);
            String url = "/email/domain/%s/account".formatted(credentials.domain());

            String json = ovhApi.post(url, body, true);

            return objectMapper.readValue(json, EmailCreatedResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Cannot write email creation body.", e);
            return null;
        } catch (OvhApiException e) {
            log.error("Cannot process email creation request.", e);
            return null;
        }
    }

    public List<String> getEmails(String domain) {
        String url = "/email/domain/%s/account".formatted(domain);
        try {
            String json = ovhApi.get(url);

            return objectMapper.readValue(json, new TypeReference<List<String>>(){});
        } catch (OvhApiException | JsonProcessingException e) {


            throw new RuntimeException(e);
        }
    }

    public void deleteEmails(String domain) {
        for (String email : getEmails(domain)) {
            String url = "/email/domain/%s/account/%s".formatted(domain, email);

            try {
                ovhApi.delete(url, "", true);
            } catch (OvhApiException e) {
                log.error("Cannot delete email account {}", email);
            }
        }
    }
}
