package com.hubert.ovhmailcreator.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hubert.ovhmailcreator.models.SaveEmailCredentials;

import java.io.IOException;

public class EmailCreatedSerializer extends StdSerializer<SaveEmailCredentials> {
    public EmailCreatedSerializer(Class<SaveEmailCredentials> t) {
        super(t);
    }

    @Override
    public void serialize(
            SaveEmailCredentials emailCreatedResponse,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", emailCreatedResponse.id());
        jsonGenerator.writeStringField("name", emailCreatedResponse.name());
        jsonGenerator.writeStringField("email", emailCreatedResponse.name() + "@" + emailCreatedResponse.domain());
        jsonGenerator.writeStringField("domain", emailCreatedResponse.domain());
        jsonGenerator.writeStringField("password", emailCreatedResponse.password());

        jsonGenerator.writeEndObject();
    }
}
