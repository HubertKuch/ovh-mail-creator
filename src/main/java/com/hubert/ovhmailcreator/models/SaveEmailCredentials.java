package com.hubert.ovhmailcreator.models;

public record SaveEmailCredentials(
        Long id,
        String name,
        String action,
        String domain,
        String password
) {}
