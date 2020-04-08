package com.software.PScheduling.service.impl;

import com.software.PScheduling.dto.MachineDTO;
import com.software.PScheduling.dto.MachineOrderLogDTO;
import com.software.PScheduling.dto.OrderDTO;
import com.software.PScheduling.dto.ProductDTO;
import com.software.PScheduling.mapper.IUserMapper;
import com.software.PScheduling.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author diaohao
 * @date 2019/10/24 10:46
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;
    @Override
    public List<OrderDTO> getOrder(){
        return userMapper.selectOriOrder();
    }
    @Override
    public List<ProductDTO> getProduct(){
        return userMapper.selectProduct();
    }
    @Override
    public Integer addOrder(OrderDTO orderDTO) {
        this.orderSplit(orderDTO);//订单处理完成并入库
        userMapper.addOrder(orderDTO);//原始订单入库
        this.doOrder();//排产处理
        return 1;
    }
    @Override
    public void doOrder(){
        this.getView();
    }
    private  void orderSplit(OrderDTO orderDTO){//循环分割订单为小订单并加入数据库
        ProductDTO productDTO = userMapper.selectProductById(orderDTO.getProductId());
        if(productDTO.getProductChildId() != null){
            String[] strId = productDTO.getProductChildId().split(",");
            String[] strRatio = productDTO.getProductChildRatio().split(",");
            for (int i = 0; i < strId.length; i++) {
                OrderDTO orderDTO1 = new OrderDTO();
                orderDTO1.setInDate(orderDTO.getInDate());
                orderDTO1.setOutDate(orderDTO.getOutDate());
                orderDTO1.setProductId(strId[i]);
                orderDTO1.setProductNum(orderDTO.getProductNum()*Integer.parseInt(strRatio[i]));
                orderDTO1.setOrderId(orderDTO.getOrderId());
                orderDTO1.setOrderDesc(orderDTO.getOrderDesc());
                ProductDTO productDTO1 = userMapper.selectProductById(orderDTO1.getProductId());
                if(productDTO1.getProductChildId() != null) {
                    this.orderSplit(orderDTO1);
                }
                else userMapper.addOkOrder(orderDTO1);
                //this.orderSplit(orderDTO1[i]);
            }
        }
        else userMapper.addOkOrder(orderDTO);
    }
    private Integer getView() {
System.out.println("进入计算");
        List<OrderDTO> orderDTO = userMapper.selectOrder();
System.out.println(orderDTO);
        //获取每一个生产产品的cr值
        HashMap<Integer, Float> crMap = new HashMap<>();
        for(int j=0;j<orderDTO.size();j++) {
            //用编号去寻找机器查出各种机器对应的效率
            List<MachineDTO> machineCap = userMapper.selectCapacity(orderDTO.get(j).getProductId());
 System.out.println(machineCap);
            //用效率去算出平均工期
            float productDateAdd = 0;
            for (int i = 0; i < machineCap.size(); i++) {
                productDateAdd = productDateAdd + orderDTO.get(j).getProductNum()/machineCap.get(i).getCapacity();//时间 = 数量/效率；叠加
            }

            float productDate = productDateAdd/machineCap.size();
            System.out.println(orderDTO.get(j).getProductId()+"的平均工期："+productDate);
            //再用平均工期算cr值
            long margin = (orderDTO.get(j).getOutDate().getTime()-orderDTO.get(j).getInDate().getTime())/(60*60*1000);//订单日期和交货日期的相差小时数,getTime()想减再除以一个数得到小时数。
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
           System.out.println("现在处理: "+minKey+" 号订单  其cr值为"+crMap.get(minKey));
           List<MachineDTO> machineCap = userMapper.selectCapacity(orderDTO.get(minKey).getProductId());
           if(this.doMachine(orderDTO.get(minKey))==1){
               System.out.println("订单排产成功");
           }
           else{
               System.out.println("排产失败");
           }
           //删除这个键值对
           crMap.remove(minKey);
       }
        return 1;
    }
    private int doMachine(OrderDTO orderDTO){//处理某一条订单，给机器安排生产计划
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
        /*
        寻找所有的可以生产的机器(找出来然后存进list里，然后)
        根据生产效率排序，从大到小，遇到相同的暂时不处理，
        判断生产机器的有空闲的开始日期是否小于订单的交货日期
        是就安排计划，判断
         */
        List<MachineDTO> machineDTOS = userMapper.selectMachine(orderDTO.getProductId());

        while(machineDTOS.size()!=0){//订单是否有适配的且空闲的机器去生产
            MachineDTO maxMachine= machineDTOS.get(0);
            int maxMachineJ = 0;
            //找到最合适的机器
            for (int i = 0; i < machineDTOS.size(); i++) {
                if(machineDTOS.get(i).getCapacity()>maxMachine.getCapacity()){
                    maxMachine = machineDTOS.get(i);
                    maxMachineJ = i;//记录在LIST中的位置
                }
            }
            //判断最合适的机器是否满足生产
            if(maxMachine.getStartTime().compareTo(orderDTO.getOutDate()) == -1){//compareTo()方法的返回值，date1小于date2返回-1，date1大于date2返回1，相等返回0;date1.compareTo(date2)
                //机器的空闲时间在产品交付之前，此时需要给这台机器安排生产
                if(((orderDTO.getOutDate().getTime()-maxMachine.getStartTime().getTime())/(60*60*1000))*maxMachine.getCapacity()>orderDTO.getProductNum()){//这台机器就已经满足了这个订单的生产
                    //此时更新订单，更新机器，输出生产计划，返回函数
                    //假定机器每天按照24小时工作，如果工作8小时，那么效率就要改变成24小时的
                    System.out.println("此时一台机器足以完成该订单在约定时间之前的生产");
                    System.out.println("订单详情为"+orderDTO);
                    System.out.println("最佳匹配机器为"+maxMachine);
                    float hours = (orderDTO.getProductNum()/maxMachine.getCapacity());
                    System.out.println("需要生产"+hours+"小时可以生产完成");
                    int minute = (int)(hours*60);//默认舍弃小时部分
                    Date newStartTimethis = this.addTimeHour(maxMachine.getStartTime(),minute);
                    changeMachine(maxMachine,maxMachine.getStartTime(),newStartTimethis,orderDTO.getOrderId(),orderDTO.getProductId());
                    return 1;
                }
                else{//这台机器不能满足这个订单的生产，此时更新机器，更新订单并且再次调用该函数用这个订单去
                    //更新订单的生产数量，但是不需要写入数据库
                    float maxProductNum = maxMachine.getCapacity()*((orderDTO.getOutDate().getTime()-maxMachine.getStartTime().getTime())/(60*60*1000));
                    System.out.println("此时这台机器无法完成对于该订单的全部生产；进行第二个机器的生产工作");
                    orderDTO.setProductNum(orderDTO.getProductNum()-(int)maxProductNum);

                    //更新机器的状态
                    changeMachine(maxMachine,maxMachine.getStartTime(),orderDTO.getOutDate(),orderDTO.getOrderId(),orderDTO.getProductId());
                    doMachine(orderDTO);
                }
            }
            else{//此时的这台机器已经不能满足生产了，所以从所有适配的机器中移除这台机器,对应的是一条订单
                System.out.println("机器的空闲时间已经超过了生产的交货期，无法进行安排生产");
                machineDTOS.remove(maxMachineJ);
            }
        }
        System.out.println("此时工厂的机器已经无法去处理这个订单了，应该进行报警处理了");
        return 0;
        //System.out.println(maxMachine);
    }
    private void  changeMachine(MachineDTO maxMachine,Date oriStartTime,Date startTime,String orderId,String  productId){//改变机器
        MachineDTO newMachine = maxMachine;
        newMachine.setProductId(productId);
        newMachine.setStartTime(startTime);
        newMachine.setOrderId(orderId);
        userMapper.changeMachine(newMachine);

        MachineOrderLogDTO machineOrderLogDTO = new MachineOrderLogDTO();
        machineOrderLogDTO.setMachineId(newMachine.getMachineId());
        machineOrderLogDTO.setProductId(newMachine.getProductId());
        machineOrderLogDTO.setOrderId(newMachine.getOrderId());
        machineOrderLogDTO.setStartTime(oriStartTime);
        machineOrderLogDTO.setEndTime(newMachine.getStartTime());
        machineOrderLogDTO.setIsDelete(0);
        userMapper.addMachineOrderLog(machineOrderLogDTO);

    }

    private Date addTimeHour(Date date, int x){
        //返回的是字符串型的时间，输入的
        //是String day, int x
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
            //引号里面个格式也可以是 HH:mm:ss或者HH:mm等等
            if (date == null)
                return null;
            System.out.println("front:" + format.format(date)); //显示输入的日期
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, x);// 24小时制
            date = cal.getTime();
            System.out.println("after:" + format.format(date));  //显示更新后的日期
            cal = null;
            return date;
    }
}
