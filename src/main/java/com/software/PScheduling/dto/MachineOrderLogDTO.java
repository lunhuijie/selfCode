package com.software.PScheduling.dto;

import java.util.Date;

public class MachineOrderLogDTO {
    private String machineId;
    private Date startTime;
    private Date endTime;
    private String productId;
    private String orderId;
    private Integer isDelete;

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "MachineOrderLogDTO{" +
                "machineId='" + machineId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", productId='" + productId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }
}
