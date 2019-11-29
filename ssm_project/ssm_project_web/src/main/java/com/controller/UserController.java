package com.controller;

import com.domain.Role;
import com.domain.Users;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    //查找所有用户
    @RequestMapping("/findAllUsers.do")
    public ModelAndView findAllUsers() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Users> list = userService.findAllUsers();
        mv.addObject("userList", list);
        mv.setViewName("user-list");
        return mv;
    }

    //添加用户
    @RequestMapping("/save.do")
    public String save(Users users) throws Exception {
        userService.save(users);
        //添加完成后重定向到user-list.jsp页面
        return "redirect:findAllUsers.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id", required = true) String id) {
        ModelAndView mv = new ModelAndView();
        Users users = userService.findById(id);
        mv.addObject("user",users);
        mv.setViewName("user-show");
        return mv;
    }

    //查询用户以及用户可以添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id",required = true) String userId){
        ModelAndView mv = new ModelAndView();
        //1、根据id查询用户
        Users users = userService.findById(userId);
        //2、根据id查询可以添加的角色
        List<Role> otherRoles = userService.findOtherRoles(userId);
        mv.addObject("user",users);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }

    //给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId",required = true) String userId,
                                @RequestParam(name = "ids",required = true) String[] rolesId) throws Exception {
        userService.addRoleToUser(userId,rolesId);
        return "redirect:findAllUsers.do";
    }
}
