package com.bincontrol.binstoreserver.service;

import com.bincontrol.binstoreserver.entity.UserEntity;
import com.bincontrol.binstoreserver.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 保存用户信息
     * @param userEntity 用户实体
     */
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    /**
     * 通过用户帐号和密码查找用户并返回用户数据
     * @param account 帐号
     * @param password 密码
     * @return 帐号密码匹配的用户实体
     */
    public UserEntity get(String account, String password) {
        return userRepository.findByAccountAndPassword(account, password);
    }

    /**
     * 查找所有用户并返回用户数据
     * @return 全部用户
     */
    public Iterable<UserEntity> getAll() {
        return userRepository.findAll();
    }

    /**
     * 在数据库中查找帐号
     * @param account 帐号
     * @return 帐号匹配的用户是否存在
     */
    public boolean find(String account) {
        UserEntity userEntity = userRepository.findByAccount(account);
        return userEntity != null;
    }

    /**
     * 在数据库中查找帐号密码
     * @param account 帐号
     * @param password 密码
     * @return 帐号密码匹配的用户是否存在
     */
    public boolean find(String account, String password) {
        UserEntity userEntity = userRepository.findByAccountAndPassword(account, password);
        return userEntity != null;
    }

}
