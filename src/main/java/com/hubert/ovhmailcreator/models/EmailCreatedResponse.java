package com.hubert.ovhmailcreator.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EmailCreatedResponse(
        Long id,
        String name,
        String action,
        String domain
) {
}
