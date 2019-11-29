package com.dao;

import com.domain.Permission;
import com.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {
    //根据id 查询所有的Role信息
    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true ,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id" ,javaType = java.util.List.class,many = @Many(select = "com.dao.PermissionDao.findPermissionsByRoleId")),
    })
    List<Role> findRolesById(String userId);


    //查询所有角色信息
    @Select("select * from role")
    List<Role> findAll() throws Exception;

    //插入角色信息
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role) throws Exception;

    //根据id查询角色信息
    @Select("select * from role where id=#{id}")
    Role findById(String id) throws Exception;

    //跟据role表中的id查询该角色还能添加哪些资源权限
    @Select("select * from permission where id not in(select permissionId from role_permission where roleId=#{id})")
    List<Permission> findOtherPermission(String id) throws Exception;

    //给role角色添加资源权限
    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId") String roleId, @Param("permissionId") String permissionId) throws Exception;
}
