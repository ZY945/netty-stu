package com.example.rpc.provider.service;

import com.example.rpc.api.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author dongfeng
 * 2024-01-15 21:29
 */
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {
    @Override
    public String login(String username, String password) {
        log.info("login username:{}, password:{}", username, password);
        return "login success";
    }
}
