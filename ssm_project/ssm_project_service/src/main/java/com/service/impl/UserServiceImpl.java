package com.service.impl;

import com.dao.UsersDao;
import com.domain.Role;
import com.domain.Users;
import com.service.UserService;
import com.utils.BCryptPasswordEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UsersDao usersDao;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users usersByUsername = usersDao.findUsersByUsername(username);
        //处理自己的Users对象封装成为UserDetails
        //User user = new User(usersByUsername.getUsername(), "{noop}"+usersByUsername.getPassword(), getAutherity(usersByUsername.getRoles()));
        User user = new User(usersByUsername.getUsername(), "{noop}"+ usersByUsername.getPassword(), usersByUsername.getStatus() == 0 ? false : true, true, true, true, getAutherity(usersByUsername.getRoles()));
        return user;
    }

    //将角色描述装入集合中
    public List<SimpleGrantedAuthority> getAutherity(List<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
        return list;
    }

    @Override
    public List<Users> findAllUsers() throws Exception {
        return usersDao.findAllUsers();
    }

    @Override
    public void save(Users users) throws Exception {
        //对用户的密码进行加密
        //users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        usersDao.save(users);
    }

    @Override
    public Users findById(String id) {
        return usersDao.findById(id);
    }

    @Override
    public List<Role> findOtherRoles(String userId) {
        return usersDao.findOtherRoles(userId);
    }

    @Override
    public void addRoleToUser(String userId, String[] rolesId) throws Exception {
        for (String roleId : rolesId) {
            usersDao.addRoleToUser(userId,roleId);
        }
    }
}
