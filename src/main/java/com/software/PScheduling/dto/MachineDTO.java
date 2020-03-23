package com.software.PScheduling.dto;

public class MachineDTO {
    private Integer machineId;
    private Integer productId;
    private Float capacity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
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
