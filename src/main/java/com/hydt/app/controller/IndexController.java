package com.hydt.app.controller;

import com.google.common.html.HtmlEscapers;
import com.hydt.app.common.Result;
import com.hydt.app.common.User;
import com.hydt.app.job.JobConfig;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bean_huang on 2017/7/5.
 */
@CrossOrigin(origins="*", allowedHeaders = "*")
@Controller
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    JobConfig jobConfig;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
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
        logger.error("/hello  获取请求参数" + request.getParameter("value"));


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


    @RequestMapping("/sql")
    public String sqlView(String sql){
        return "sqlView";
    }

    @PostMapping("/querySql")
    @ResponseBody
    public Object querySql(String text){
        logger.error("querySql(text): " + text);
        jdbcTemplate.query(text, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {

            }
        });
        return text;
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> doLogin(String name, String password){
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/startJob")
    @ResponseBody
    public Object startJob(String name){
        jobConfig.startJob(name);
        return Result.sucess(1,"OK",null);
    }

    @RequestMapping("/stopJob")
    @ResponseBody
    public Object stopJob(String name){
        jobConfig.stopJob(name);
        return Result.sucess(1,"OK",null);
    }

}
