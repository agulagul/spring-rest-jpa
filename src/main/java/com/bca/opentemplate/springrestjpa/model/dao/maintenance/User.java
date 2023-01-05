package com.bca.opentemplate.springrestjpa.model.dao.maintenance;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.bca.opentemplate.springrestjpa.model.dao.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "mt_users", schema = "public")
public class User extends BaseEntity {
  @Id
  @Column(name = "user_id", unique = true, nullable = false)
  private UUID userId;

  @Column(name = "user_name", unique = true, nullable = false)
  private String userName;

  @Column(name = "password", nullable = false)
  private String password;

  @PrePersist
  public void prePersist() {
    super.prePersist();
    this.userId = UUID.randomUUID();
    this.password = "bcabca";
  }

  @PreUpdate
  public void preUpdate() {
    super.preUpdate();
  }
}
