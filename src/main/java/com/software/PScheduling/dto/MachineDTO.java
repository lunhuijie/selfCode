package com.software.PScheduling.dto;

import java.util.Date;

public class MachineDTO {
    private String  machineId;
    private String  productId;
    private String  orderId;
    private Float capacity;
    private Date startTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "MachineDTO{" +
                "machineId=" + machineId +
                ", productId=" + productId +
                ", capacity=" + capacity +
                '}';
    }
}
