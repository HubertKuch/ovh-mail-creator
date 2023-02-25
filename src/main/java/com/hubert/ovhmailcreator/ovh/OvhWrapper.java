package com.hubert.ovhmailcreator.ovh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubert.ovhmailcreator.configuration.OvhConfiguration;
import com.hubert.ovhmailcreator.external.OvhApi;
import com.hubert.ovhmailcreator.external.OvhApiException;
import com.hubert.ovhmailcreator.models.CreateEmailCredentials;
import com.hubert.ovhmailcreator.models.EmailCreatedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OvhWrapper {
    private final OvhConfiguration ovhConfiguration;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmailCreatedResponse createEmail(CreateEmailCredentials credentials) {
        OvhApi ovhApi = new OvhApi(ovhConfiguration.getEndpoint(),
                                   ovhConfiguration.getAppKey(),
                                   ovhConfiguration.getAppSecret(),
                                   ovhConfiguration.getConsumerKey()
        );

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

    public void getEmails(String domain) {
        OvhApi ovhApi = new OvhApi(ovhConfiguration.getEndpoint(),
                                   ovhConfiguration.getAppKey(),
                                   ovhConfiguration.getAppSecret(),
                                   ovhConfiguration.getConsumerKey()
        );

        String url = "/email/domain/%s/account".formatted(domain);
        String json = null;
        try {
            json = ovhApi.get(url);
        } catch (OvhApiException e) {
            throw new RuntimeException(e);
        }
        System.out.println(json);

//            return objectMapper.reader().readValue(json);
    }
}
