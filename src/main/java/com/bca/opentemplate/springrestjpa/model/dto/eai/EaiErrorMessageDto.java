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
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class EaiErrorMessageDto {

    @JsonProperty(value = "indonesian")
    private String indonesian;

    @JsonProperty(value = "english")
    private String english;

}
