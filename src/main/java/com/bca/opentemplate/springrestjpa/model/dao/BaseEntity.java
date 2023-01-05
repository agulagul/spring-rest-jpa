package com.bca.opentemplate.springrestjpa.model.dao;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
public class BaseEntity {
  @Column(name = "created_date", nullable = false)
  private Long createdDate;

  @Column(name = "created_by", nullable = false)
  private String createdBy;

  @Column(name = "updated_date")
  private Long updatedDate;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "version")
  private Integer version;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @PrePersist
  public void prePersist() {
    this.createdBy = "SYSTEM";
    this.createdDate = Instant.now().toEpochMilli();
    this.version = 1;
    this.isActive = true;
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedBy = "SYSTEM";
    this.updatedDate = Instant.now().toEpochMilli();
    this.version++;
  }
}
