package com.bca.opentemplate.springrestjpa.model.dto.eai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EaiErrorSchemeDto {

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_message")
    private EaiErrorMessageDto eaiErrorMessageDto;
}
