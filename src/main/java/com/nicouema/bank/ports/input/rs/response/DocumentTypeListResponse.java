package com.nicouema.bank.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nicouema.bank.ports.input.rs.response.DocumentTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeListResponse {

    private List<DocumentTypeResponse> content = null;

    @JsonProperty("next_uri")
    private String nextUri;

    @JsonProperty("previous_uri")
    private String previousUri;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_elements")
    private Long totalElements;
}
