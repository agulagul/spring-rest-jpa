package com.bca.opentemplate.springrestjpa.model.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {

  @JsonProperty("user_id")
  private String userId;

  @JsonProperty("user_name")
  private String userName;

  @JsonProperty("created_date")
  private Long createdDate;

  @JsonProperty("created_by")
  private String createdBy;

  @JsonProperty("updated_date")
  private Long updatedDate;

  @JsonProperty("updated_by")
  private String updatedBy;

  @JsonProperty("version")
  private Integer version;

  @JsonProperty("is_active")
  private Boolean isActive;

}
