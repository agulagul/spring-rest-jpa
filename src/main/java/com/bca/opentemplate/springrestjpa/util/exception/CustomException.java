package com.bca.opentemplate.springrestjpa.util.exception;

import com.bca.opentemplate.springrestjpa.model.dto.eai.EaiResponseDto;
import com.bca.opentemplate.springrestjpa.util.http.HttpResponseMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private EaiResponseDto<Object> eaiResponseDto;

    public CustomException() {
        super();
    }

    public CustomException(HttpResponseMapping httpResponseMapping, Object content) {
        super(httpResponseMapping.eaiErrorSchemeDto().getEaiErrorMessageDto().getEnglish());
        this.eaiResponseDto = new EaiResponseDto<>(httpResponseMapping, content);
    }
}
