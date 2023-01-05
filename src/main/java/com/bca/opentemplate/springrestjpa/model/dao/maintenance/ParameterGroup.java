package com.bca.opentemplate.springrestjpa.model.dao.maintenance;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.bca.opentemplate.springrestjpa.model.dao.BaseEntityUserAuditor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "mt_param_groups", schema = "public")
public class ParameterGroup extends BaseEntityUserAuditor {
  @Id
  @Column(name = "param_group_id", nullable = false)
  private UUID paramGroupId;

  @Column(name = "param_group_code", nullable = false)
  private String paramGroupCode;

  @Column(name = "param_group_name", nullable = false)
  private String paramGroupName;

  @Column(name = "param_group_desc")
  private String paramGroupDesc;

  @Column(name = "parent_id")
  private UUID parentId;

  @PrePersist
  public void prePersist() {
    super.prePersist();
    this.paramGroupId = UUID.randomUUID();
  }

  @PreUpdate
  public void preUpdate() {
    super.preUpdate();
  }

}
