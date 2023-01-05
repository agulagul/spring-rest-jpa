package com.bca.opentemplate.springrestjpa.model.dao.maintenance;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "mt_param_details", schema = "public")
public class ParameterDetail extends BaseEntityUserAuditor {

  @Id
  @Column(name = "param_detail_id", nullable = false)
  private UUID paramDetailId;

  @Column(name = "param_detail_code", nullable = false)
  private String paramDetailCode;

  @Column(name = "param_detail_name", nullable = false)
  private String paramDetailName;

  @Column(name = "param_detail_desc")
  private String paramDetailDesc;

  @Column(name = "parent_id")
  private UUID parentId;

  @ManyToOne
  @JoinColumn(name = "param_group_id", nullable = false)
  private ParameterGroup paramGroup;

  @PrePersist
  public void prePersist() {
    super.prePersist();
    this.paramDetailId = UUID.randomUUID();
  }

  @PreUpdate
  public void preUpdate() {
    super.preUpdate();
  }
}
