package com.bca.opentemplate.springrestjpa.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class BaseResponseDto {
    @JsonProperty(value = "meta_pagination")
    protected MetaPaginationDto metaPaginationDto;

    @JsonProperty(value = "result")
    protected Object result;
}
