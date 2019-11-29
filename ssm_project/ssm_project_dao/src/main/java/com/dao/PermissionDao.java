package com.dao;

import com.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionDao {
    //根据角色ID查询权限信息（借助中间表role_permission）
    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{id})")
    List<Permission> findPermissionsByRoleId(String id);

    //查询所有的权限信息
    @Select("select * from permission")
    List<Permission> findAll() throws Exception;

    //添加权限信息
    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    void save(Permission permission) throws Exception;
}
