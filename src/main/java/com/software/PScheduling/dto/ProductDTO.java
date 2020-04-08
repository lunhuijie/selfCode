package com.software.PScheduling.dto;

public class ProductDTO {
    private String productId;
    private String productName;
    private String productChildId;
    private String productChildRatio;
    private String desc;

    public String getProductChildRatio() {
        return productChildRatio;
    }

    public void setProductChildRatio(String productChildRatio) {
        this.productChildRatio = productChildRatio;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductChildId() {
        return productChildId;
    }

    public void setProductChildId(String productChildId) {
        this.productChildId = productChildId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productChileId='" + productChildId + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
