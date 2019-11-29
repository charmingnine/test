package com.dao;

import com.domain.Role;
import com.domain.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UsersDao {

    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id", javaType = java.util.List.class, many = @Many(select = "com.dao.RoleDao.findRolesById"))
    })
    Users findUsersByUsername(String username);

    //查找所有用户
    @Select("select * from users")
    List<Users> findAllUsers() throws Exception;

    //添加用户
    @Insert("insert into users(username,password,email,phoneNum,status) " +
            "values(#{username},#{password},#{email},#{phoneNum},#{status})")
    void save(Users users) throws Exception;

    //根据用户id查询详细信息
    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id", javaType = java.util.List.class, many = @Many(select = "com.dao.RoleDao.findRolesById"))
    })
    Users findById(String id);

    //根据用户Id查询用户还可以添加的角色
    @Select("select * from role where id not in (select roleId from users_role where userId=#{userId})")
    List<Role> findOtherRoles(String userId);

    //给用户添加角色
    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleId")String roleId) throws Exception;
}
