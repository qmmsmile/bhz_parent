package com.qmm.controller;

import com.qmm.entity.User;
import com.qmm.service.UserService;
import com.qmm.common.ResponseMessage;
import com.qmm.vo.MyUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qinmm on 2017/6/7.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    //前后端不分离
    @RequestMapping(value = "/showUser",method = RequestMethod.GET)
    public String toIndex(HttpServletRequest request, Model model){
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(userId);
        model.addAttribute("user", user);
        logger.info("user==================>"+user);
        return "showUser";
    }

    //前后端分离
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage showUser(Integer id){
        User user = this.userService.getUserById(id);
        MyUser myUser = new MyUser();
        myUser.setId(user.getId());
        myUser.setName(user.getUserName());
        myUser.setAge(user.getAge());
        logger.info("myUser==================>"+myUser.getName());
        return new ResponseMessage().success().packageData("list",myUser);
    }


}
