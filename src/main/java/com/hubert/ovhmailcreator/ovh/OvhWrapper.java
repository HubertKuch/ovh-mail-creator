package com.hubert.ovhmailcreator.ovh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubert.ovhmailcreator.configuration.OvhConfiguration;
import com.hubert.ovhmailcreator.external.OvhApi;
import com.hubert.ovhmailcreator.external.OvhApiException;
import com.hubert.ovhmailcreator.models.CreateEmailCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OvhWrapper {
    private final OvhConfiguration ovhConfiguration;
    private final ObjectMapper objectMapper;

    public void createEmail(CreateEmailCredentials credentials) {
        OvhApi ovhApi = new OvhApi(ovhConfiguration.getEndpoint(),
                                   ovhConfiguration.getAppKey(),
                                   ovhConfiguration.getAppSecret(),
                                   ovhConfiguration.getConsumerKey()
        );

        try {
            String body = objectMapper.writeValueAsString(credentials);
            String path = "/email/domain/%s/account".formatted(credentials.domain());

            String json = ovhApi.post(path, body, true);

            System.out.println(json);
        } catch (JsonProcessingException e) {
            log.error("Cannot write email creation body.");
        } catch (OvhApiException e) {
            log.error("Cannot process email creation request.");
        }
    }
}
