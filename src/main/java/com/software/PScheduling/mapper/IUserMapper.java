package com.software.PScheduling.mapper;
import com.software.PScheduling.dto.MachineDTO;
import com.software.PScheduling.dto.MachineOrderLogDTO;
import com.software.PScheduling.dto.OrderDTO;
import com.software.PScheduling.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author diaohao
 * @date 2019/10/24 10:32
 */
@Mapper
@Component
public interface IUserMapper {


    List<MachineDTO> selectCapacity(String productNo);
    List<OrderDTO> selectOrder();
    List<OrderDTO> selectOriOrder();
    List<MachineDTO> selectMachine(String productNo);
    List<ProductDTO> selectProduct();
    ProductDTO selectProductById(String productId);
    Integer addOrder(OrderDTO orderDTO);
    Integer addOkOrder(OrderDTO orderDTO);
    void addMachineOrderLog(MachineOrderLogDTO machineOrderLogDTO);
    void changeMachine(MachineDTO machineDTO);
}
