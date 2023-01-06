package com.bca.opentemplate.springrestjpa.model.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParameterDetailRequestDto {
  
  @JsonProperty("param_detail_code")
  private String paramDetailCode;
  
  @JsonProperty("param_detail_name")
  private String paramDetailName;
  
  @JsonProperty("param_detail_desc")
  private String paramDetailDesc;
  
  @JsonProperty("parent_id")
  private String parentId;

  @JsonProperty("param_group_id")
  private String parameterGroupId;

  @JsonProperty("created_by")
  private String createdBy;

  @JsonProperty("updated_by")
  private String updatedBy;
}
