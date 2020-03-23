package com.software.PScheduling.service.impl;

import com.software.PScheduling.dto.MachineDTO;
import com.software.PScheduling.dto.OrderDTO;
import com.software.PScheduling.mapper.IUserMapper;
import com.software.PScheduling.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author diaohao
 * @date 2019/10/24 10:46
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;
    @Override
    public Integer getView() {
System.out.println("进入计算");
        List<OrderDTO> orderDTO = userMapper.selectOrder();
System.out.println(orderDTO);
        //获取每一个生产产品的cr值
        HashMap<Integer, Float> crMap = new HashMap<>();
        for(int j=0;j<orderDTO.size();j++) {
            //用编号去寻找机器查出各种机器对应的效率
            List<MachineDTO> machineCap = userMapper.selectCapacity(orderDTO.get(j).getProductNo());
 System.out.println(machineCap);
            //用效率去算出平均工期
            float productDateAdd = 0;
            for (int i = 0; i < machineCap.size(); i++) {
                productDateAdd = productDateAdd + orderDTO.get(j).getProductNum()/machineCap.get(i).getCapacity();//时间 = 数量/效率；叠加
            }
 System.out.println(orderDTO.get(j).getProductNo()+"的平均工期："+productDateAdd);
            float productDate = productDateAdd/machineCap.size();
            //再用平均工期算cr值
            long margin = (orderDTO.get(j).getOutDate().getTime()-orderDTO.get(j).getInDate().getTime())/(60*60*1000);//订单日期和交货日期的相差小时数
            crMap.put(j,margin/productDate);
            //用cr值高的去寻找机器生产
        }
        //cr值排序
       while(!crMap.isEmpty())
       {
           //找到最小CR value处理
           float minValue = -1;//cr值不可能为负数，所以直接初始化为负数
           int minKey = -1;//键值为第几条订单，所以不可能为负数
           for (Map.Entry<Integer, Float> entry : crMap.entrySet()) {
               minValue = entry.getValue();//继续初始化为第一个map的value
               minKey = entry.getKey();
               if(minValue != -1){
                  break;//设置好为第一个就可以直接退出这个循环
               }
           }
           //找到最小的crmap中的key和value
           for (Map.Entry<Integer, Float> entry : crMap.entrySet()) {
               if(entry.getValue()<minValue){
                   minValue = entry.getValue();
                   minKey = entry.getKey();
               }
           }
           //去匹配机器有这个订单的详细信息了key为第几笔订单即list<key>的那个订单可以优先处理
           System.out.println("最小cr值对应的生产订单号:"+minKey+"  生产该订单产品  其cr值为"+crMap.get(minKey));
           List<MachineDTO> machineCap = userMapper.selectCapacity(orderDTO.get(minKey).getProductNo());
           doMachine(orderDTO.get(minKey));
           //删除这个键值对
           crMap.remove(minKey);
       }
        return 1;
    }
    private void doMachine(OrderDTO orderDTO){//处理某一条订单，给机器安排生产计划
        /*
        寻找所有的可以生产的机器
        根据生产效率排序，从大到小
        判断生产机器的开始日期是否小于订单的交货日期
        是就安排计划，判断
         */
    }
}
