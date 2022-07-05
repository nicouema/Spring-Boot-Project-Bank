package com.nicouema.bank.ports.input.rs.api;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.function.Function;

public interface ApiConstants {

    String DOCUMENT_TYPE_URI="/document-types";
    String USER_URI="/users";
    String AUTHENTICATION_URI="/auth";

    Function<Integer, String> uriByPageAsString = (page) ->
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", page).toUriString();

    int DEFAULT_PAGE = 0;
    int DEFAULT_PAGE_SIZE = 10;
}
