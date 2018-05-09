package com.bincontrol.binstoreserver.repository;

import com.bincontrol.binstoreserver.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    /**
     * 通过帐号查找用户
     * @param account 帐号
     * @return 帐号匹配的用户实体
     */
    UserEntity findByAccount(String account);

    /**
     * 通过帐号和密码查找用户
     * @param account 帐号
     * @param password 密码
     * @return 帐号密码匹配的用户实体
     */
    UserEntity findByAccountAndPassword(String account, String password);

}
