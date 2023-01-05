package com.bca.opentemplate.springrestjpa.model.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParameterGroupResponseDto {
  @JsonProperty("param_group_id")
  private String paramGroupId;

  @JsonProperty("param_group_code")
  private String paramGroupCode;

  @JsonProperty("param_group_name")
  private String paramGroupName;

  @JsonProperty("param_group_desc")
  private String paramGroupDesc;

  @JsonProperty("parent_id")
  private String parentId;

  @JsonProperty("created_date")
  private Long createdDate;

  @JsonProperty("created_by")
  private UserResponseDto createdBy;

  @JsonProperty("updated_date")
  private Long updatedDate;

  @JsonProperty("updated_by")
  private UserResponseDto updatedBy;

  @JsonProperty("version")
  private Integer version;

  @JsonProperty("is_active")
  private Boolean isActive;
}
