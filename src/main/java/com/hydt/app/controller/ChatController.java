package com.hydt.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by bean_huang on 2017/10/11.
 */
@Controller
@RequestMapping("chat")
public class ChatController {

    @RequestMapping({"","/"})
    public String index(){
        return "chat";
    }
}
