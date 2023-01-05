package com.bca.opentemplate.springrestjpa.repository.maintenance;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bca.opentemplate.springrestjpa.model.dao.maintenance.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  List<User> findAll();
  List<User> findAllByUserName(String userName);
}
