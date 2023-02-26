package com.hubert.ovhmailcreator.randomwords;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Slf4j
public class RandomWordsWrapper {
    private String baseUrl = "https://randomword.com";

    public String randomAdjective() {
        try {
            Document document = Jsoup.connect(baseUrl + "/adjective").get();

            return document.getElementById("#random_word").text();
        } catch (NullPointerException e) {
            log.error("Cannot read #random_word");
            return "";
        } catch (IOException e) {
            log.error("Cannot connect to random words api");
            return "";
        }
    }

}
