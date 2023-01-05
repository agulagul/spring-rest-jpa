package com.bca.opentemplate.springrestjpa.model.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestDto {

  @JsonProperty("user_id")
  private String userId;

  @JsonProperty("user_name")
  private String userName;
}
