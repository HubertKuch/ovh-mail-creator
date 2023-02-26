package com.hubert.ovhmailcreator.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hubert.ovhmailcreator.configuration.OvhConfiguration;
import com.hubert.ovhmailcreator.models.CreateEmailCredentials;
import com.hubert.ovhmailcreator.models.EmailCreatedResponse;
import com.hubert.ovhmailcreator.models.SaveEmailCredentials;
import com.hubert.ovhmailcreator.ovh.OvhWrapper;
import com.hubert.ovhmailcreator.randomwords.RandomWordsWrapper;
import com.hubert.ovhmailcreator.serializers.EmailCreatedSerializer;
import com.hubert.ovhmailcreator.utils.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class EmailCommands {
    private final OvhConfiguration ovhConfiguration;

    @ShellMethod(key = "create-email", value = "Create emails")
    public String createEmail(
            @ShellOption(help = "OVH domain", value = "--domain") String domain,
            @ShellOption(help = "How many accounts", value = "--count") Integer count,
            @ShellOption(help = "Password for every one", value = "--password") String password,
            @ShellOption(help = "Email base for example `test` and email will be created from `test_{generateValue}`", value = "--base", defaultValue = "") String base,
            @ShellOption(help = "Description", value = "--description", defaultValue = "") String description,
            @ShellOption(help = "Size in bytes", value = "--size", defaultValue = "100000000") Long size,
            @ShellOption(help = "Random string length", value = "--hash-length", defaultValue = "8") Integer hashLength,
            @ShellOption(help = "Separator between email base and random hash", value = "--separator", defaultValue = "") String separator,
            @ShellOption(help = "Base will be random word", value = "--random-base-word", defaultValue = "true") boolean isRandomBaseWord
    ) throws IOException {
        OvhWrapper ovhWrapper = new OvhWrapper(ovhConfiguration);
        List<EmailCreatedResponse> emails = List.of(new EmailCreatedResponse(24142L, "test", "test", "test.com"),
                new EmailCreatedResponse(24142L, "test", "test", "test.com"),
                new EmailCreatedResponse(24142L, "test", "test", "test.com")
        );
        String workingDir = System.getProperty("user.dir");
        RandomWordsWrapper randomWordsWrapper = new RandomWordsWrapper();

        for (int index = 0; index < count; index++) {
            if (isRandomBaseWord) {
                base = randomWordsWrapper.randomAdjective();
            }

            String name = base + separator + Hashing.randomString(hashLength);

            CreateEmailCredentials createEmailCredentials = new CreateEmailCredentials(domain,
                    name,
                    description,
                    password,
                    size
            );
            emails.add(ovhWrapper.createEmail(createEmailCredentials));
        }

        String pathname = workingDir + "/emails.json";
        File file = new File(pathname);

                if (file.exists()) {
                    log.error("File emails.json already exists.");
                    return "";
                }

                boolean isCreated = file.createNewFile();

                if (!isCreated) {
                    log.error("Cannot create emails file");
                    return "";
                }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule serializer = new SimpleModule().addSerializer(SaveEmailCredentials.class,
                new EmailCreatedSerializer(SaveEmailCredentials.class)
        );

        objectMapper.registerModule(serializer);

        String fileContent = objectMapper.writeValueAsString(emails.stream()
                                                                   .map(email -> new SaveEmailCredentials(email.id(),
                                                                           email.name(),
                                                                           email.action(),
                                                                           email.domain(),
                                                                           password
                                                                   )).toList());

        bufferedWriter.append(fileContent);

        bufferedWriter.close();

        return "Written into " + pathname;
    }

    @ShellMethod(key = "get-emails", value = "Get emails")
    public List<String> getEmails(
            @ShellOption(help = "OVH domain", value = "--domain") String domain
    ) {
        OvhWrapper ovhWrapper = new OvhWrapper(ovhConfiguration);

        return ovhWrapper.getEmails(domain);
    }

    @ShellMethod(key = "delete-emails", value = "Delete emails from domain")
    public void deleteAll(
            @ShellOption(help = "OVH domain", value = "--domain") String domain
    ) {
        OvhWrapper ovhWrapper = new OvhWrapper(ovhConfiguration);

        ovhWrapper.deleteEmails(domain);
    }

}
