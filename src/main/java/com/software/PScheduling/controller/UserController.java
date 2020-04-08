package com.software.PScheduling.controller;


import com.software.PScheduling.dto.OrderDTO;
import com.software.PScheduling.dto.ProductDTO;
import com.software.PScheduling.service.IUserService;
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
        userService.doOrder();
        System.out.println("前端正常");
    }
    @PostMapping(value = "/getOrder")
    public List<OrderDTO> getOrder(){
        return userService.getOrder();
    }
    @PostMapping(value = "/getProduct")
    public List<ProductDTO> getProduct(){
        System.out.println(userService.getProduct());
        return userService.getProduct();
    }
    @PostMapping(value = "/addOrder")
    public Integer addOrder(@RequestBody OrderDTO orderDTO){
        System.out.println(orderDTO);
        userService.addOrder(orderDTO);
        return 1;
    }








}
