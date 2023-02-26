package com.hubert.ovhmailcreator.randomwords;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomWordsWrapper {

    public String randomAdjective() {
        try {
            File file = ResourceUtils.getFile("classpath:adjectives.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            String line = reader.readLine();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            return lines.get(new Random().nextInt(lines.size()));
        } catch (FileNotFoundException e) {
            log.error("Cannot read adjectives. Not Found");
            return "";
        } catch (IOException e) {
            log.error("Cannot read adjectives. IO Exception");
            return "";
        }
    }

}
