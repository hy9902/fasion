package com.hydt.app.controller;

import com.hydt.app.common.Result;
import com.hydt.app.common.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by bean_huang on 2017/7/5.
 */
@Controller
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("")
    public String index(){
        return "index";
    }


    @RequestMapping("session")
    @ResponseBody
    public Result index(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        return Result.sucess(1,"OK","sessionId :" + httpSession.getId());
    }

    @RequestMapping("hello/{uid}")
    @ResponseBody
    public Result hello(HttpServletRequest request, @PathVariable(required = false)Long uid){
        HttpSession httpSession = request.getSession();
        User user = new User();
        user.setId(uid);
        user.setAge(20);
        user.setName("ann");
        user.setBirthDate(new Date());
        return Result.sucess(1,"OK",user);
    }

    @RequestMapping("/redirect")
    public String redirect(){
        return "redirect";
    }

}
