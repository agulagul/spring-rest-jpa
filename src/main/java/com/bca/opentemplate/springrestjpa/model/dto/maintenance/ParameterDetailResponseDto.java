package com.bca.opentemplate.springrestjpa.model.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParameterDetailResponseDto {

  @JsonProperty("param_detail_id")
  private String paramDetailId;
  
  @JsonProperty("param_detail_code")
  private String paramDetailCode;
  
  @JsonProperty("param_detail_name")
  private String paramDetailName;
  
  @JsonProperty("param_detail_desc")
  private String paramDetailDesc;
  
  @JsonProperty("parent_id")
  private String parentId;

  @JsonProperty("param_group")
  private ParameterGroupResponseDto paramGroup;
    
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
