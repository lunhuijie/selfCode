package com.software.PScheduling.controller;


import com.software.PScheduling.dto.OrderDTO;
import com.software.PScheduling.service.IUserService;
import com.software.PScheduling.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author diaohao
 * @date 2020/1/24 10:32
 */
//跨域问题解决注解
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @PostMapping(value = "/getView")
    public void getView(){
        System.out.println(userService.getView());
        System.out.println("前端正常");
    }





}
