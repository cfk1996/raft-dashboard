package com.github.chenfeikun.raftdashboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desciption: HelloController
 * @CreateTime: 2019-04-06
 * @author: chenfeikun
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String isAlive() {
        return "Raft Dashboard is running...";
    }
}
