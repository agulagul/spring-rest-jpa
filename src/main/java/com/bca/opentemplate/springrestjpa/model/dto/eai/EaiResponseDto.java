package com.bca.opentemplate.springrestjpa.model.dto.eai;

import org.springframework.http.HttpStatus;

import com.bca.opentemplate.springrestjpa.util.http.HttpResponseMapping;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EaiResponseDto<O> {

    @JsonIgnore
    private HttpStatus httpStatus;

    @JsonProperty("error_schema")
    private EaiErrorSchemeDto eaiErrorSchemaDto;

    @JsonProperty("output_schema")
    private O outputSchema;

    public EaiResponseDto(HttpStatus httpStatus, EaiErrorSchemeDto eaiErrorSchemeDto) {
        this.httpStatus = httpStatus;
        this.eaiErrorSchemaDto = eaiErrorSchemeDto;
    }

    public EaiResponseDto(HttpStatus httpStatus, EaiErrorSchemeDto eaiErrorSchemeDto, O outputSchema) {
        this.httpStatus = httpStatus;
        this.eaiErrorSchemaDto = eaiErrorSchemeDto;
        this.outputSchema = outputSchema;
    }

    public EaiResponseDto(HttpResponseMapping httpResponseMapping) {
        this.httpStatus = httpResponseMapping.httpStatus();
        this.eaiErrorSchemaDto = httpResponseMapping.eaiErrorSchemeDto();
    }

    public EaiResponseDto(HttpResponseMapping httpResponseMapping, O outputSchema) {
        this.httpStatus = httpResponseMapping.httpStatus();
        this.eaiErrorSchemaDto = httpResponseMapping.eaiErrorSchemeDto();
        this.outputSchema = outputSchema;
    }

}
