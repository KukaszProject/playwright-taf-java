package com.demo.taf.api.models;

public record ClaimPayload (
    String name,
    String job,
    String treatyId,
    double reservedAmount
) {}
