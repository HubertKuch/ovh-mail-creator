package com.hubert.ovhmailcreator.models;

import com.fasterxml.jackson.annotation.JsonGetter;

public record CreateEmailCredentials(
        @JsonGetter String domain,
        String accountName,
        String description,
        String password,
        Long size) {
}
