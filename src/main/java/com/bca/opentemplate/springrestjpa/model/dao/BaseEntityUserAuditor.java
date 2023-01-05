package com.bca.opentemplate.springrestjpa.model.dao;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bca.opentemplate.springrestjpa.model.dao.maintenance.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@MappedSuperclass
@DynamicUpdate
@DynamicInsert
public class BaseEntityUserAuditor {
  @Column(name = "created_date", nullable = false)
  private Long createdDate;

  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false)
  private User createdBy;

  @Column(name = "updated_date")
  private Long updatedDate;

  @ManyToOne
  @JoinColumn(name = "updated_by")
  private User updatedBy;

  @Column(name = "version")
  private Integer version;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @PrePersist
  public void prePersist() {
    this.createdDate = Instant.now().toEpochMilli();
    this.version = 1;
    this.isActive = true;
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedDate = Instant.now().toEpochMilli();
    this.version++;
  }
}
