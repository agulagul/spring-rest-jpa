package com.bca.opentemplate.springrestjpa.model.dto;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetaPaginationDto {
    private Integer page;

    @JsonProperty("total_pages")
    private Integer totalPages;

    private Long elements;

    public MetaPaginationDto(Page<?> page) {
        this.page = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.elements = page.getTotalElements();
    }
}
