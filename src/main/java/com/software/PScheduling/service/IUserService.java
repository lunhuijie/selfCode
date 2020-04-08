package com.software.PScheduling.service;


import com.software.PScheduling.dto.OrderDTO;
import com.software.PScheduling.dto.ProductDTO;

import java.util.List;

/**
 * @author diaohao
 * @date 2019/10/24 10:47
 */
public interface IUserService {
    List<OrderDTO> getOrder();
    List<ProductDTO> getProduct();
    Integer addOrder(OrderDTO orderDTO);

    void doOrder();
}
