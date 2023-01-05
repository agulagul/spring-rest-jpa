package com.bca.opentemplate.springrestjpa.model.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParameterGroupRequestDto {

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

  @JsonProperty("created_by")
  private String createdBy;

  @JsonProperty("updated_by")
  private String updatedBy;
}
