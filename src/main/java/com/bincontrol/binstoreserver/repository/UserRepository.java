package com.bincontrol.binstoreserver.repository;

import com.bincontrol.binstoreserver.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
