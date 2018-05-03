package com.bincontrol.binstoreserver.repository;

import com.bincontrol.binstoreserver.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
