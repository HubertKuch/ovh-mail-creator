package com.hubert.ovhmailcreator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record CreateEmailCredentials(
        @JsonIgnore String domain,
        String accountName,
        String description,
        String password,
        Long size) {
}
