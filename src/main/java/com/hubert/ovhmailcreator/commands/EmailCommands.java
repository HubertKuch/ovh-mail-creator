package com.hubert.ovhmailcreator.commands;

import com.hubert.ovhmailcreator.configuration.OvhConfiguration;
import com.hubert.ovhmailcreator.models.CreateEmailCredentials;
import com.hubert.ovhmailcreator.models.EmailCreatedResponse;
import com.hubert.ovhmailcreator.ovh.OvhWrapper;
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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class EmailCommands {
    private final OvhConfiguration ovhConfiguration;
// create-email --count 1 --base ratysh --password Testspsda2341 --domain inotomia.pl
    @ShellMethod(key = "create-email", value = "Create emails")
    public String createEmail(
            @ShellOption(help = "OVH domain", value = "--domain") String domain,
            @ShellOption(help = "How many accounts", value = "--count") Integer count,
            @ShellOption(help = "Password for every one", value = "--password") String password,
            @ShellOption(help = "Email base for example `test` and email will be created from `test_{generateValue}`", value = "--base") String base,
            @ShellOption(help = "Description", value = "--description", defaultValue = "") String description,
            @ShellOption(help = "Size in bytes", value = "--size", defaultValue = "100000000") Long size
    ) throws IOException {
        OvhWrapper ovhWrapper = new OvhWrapper(ovhConfiguration);
        List<EmailCreatedResponse> emails = new ArrayList<>();
        String workingDir = System.getProperty("user.dir");

        for (int index = 0; index < count; index++) {
            String name = base + "_" + Hashing.randomString(10);
            CreateEmailCredentials createEmailCredentials = new CreateEmailCredentials(domain, name, description, password, size);
            emails.add(ovhWrapper.createEmail(createEmailCredentials));
        }

        String pathname = workingDir + "/emails_" + System.currentTimeMillis() + ".txt";
        File file = new File(pathname);
        file.createNewFile();

        String fileContent = emails.stream().reduce("", (prev, curr) -> prev + "" + curr.name() + "@" + curr.domain() + "\n", String::concat);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

        bufferedWriter.append(fileContent);

        bufferedWriter.close();

        return "Written into " + pathname;
    }

    @ShellMethod(key = "get-emails", value = "Get emails")
    public void getEmails(
            @ShellOption(help = "OVH domain", value = "--domain") String domain
    ) {
        OvhWrapper ovhWrapper = new OvhWrapper(ovhConfiguration);
        ovhWrapper.getEmails(domain);
    }
}
