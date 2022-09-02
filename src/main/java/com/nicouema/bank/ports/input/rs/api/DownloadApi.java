package com.nicouema.bank.ports.input.rs.api;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;

@Validated
public interface DownloadApi {

    ResponseEntity<Resource> downloadClientsCsv() throws IOException, IllegalAccessException;

    ResponseEntity<Resource> downloadAccountsCsv() throws IOException, IllegalAccessException;

    ResponseEntity<Resource> downloadStatementsCsv() throws IOException, IllegalAccessException;
}
