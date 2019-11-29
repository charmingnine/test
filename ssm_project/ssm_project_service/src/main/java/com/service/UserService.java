package com.service;

import com.domain.Role;
import com.domain.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    //查询用户
    List<Users> findAllUsers() throws Exception;
    //添加用户
    void save(Users users) throws Exception;
    //根据用户的id查询详细信息
    Users findById(String id);

    List<Role> findOtherRoles(String userId);

    void addRoleToUser(String userId, String[] rolesId) throws Exception;
}
