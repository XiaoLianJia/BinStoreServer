package com.bincontrol.binstoreserver.service;

import com.bincontrol.binstoreserver.entity.User;
import com.bincontrol.binstoreserver.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

}
