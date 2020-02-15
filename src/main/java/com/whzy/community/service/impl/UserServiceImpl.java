package com.whzy.community.service.impl;

import com.whzy.community.domain.User;
import com.whzy.community.repository.UserRepository;
import com.whzy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void insertUser(User user) {
        userRepository.save(user);
    }
}
