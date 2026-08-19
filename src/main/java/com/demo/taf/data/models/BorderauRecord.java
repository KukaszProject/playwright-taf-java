package com.demo.taf.data.models;

public record BorderauRecord (
    String claimId,
    String cedentName,
    String treatyRef,
    double reserveAmount,
    String status
)
{}
