package com.software.PScheduling.dto;

import java.util.Date;

public class OrderDTO {

        private String productName;//产品名字
        private String productId;//产品编号
        private Date inDate;//当前日期
        private Date outDate;//交货日期
        private int productNum;//生产数量
        private String orderId;//订单编号
        private  String orderDesc;

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Date getInDate() {
            return inDate;
        }

        public void setInDate(Date inDate) {
            this.inDate = inDate;
        }

        public Date getOutDate() {
            return outDate;
        }

        public void setOutDate(Date outDate) {
            this.outDate = outDate;
        }

        public int getProductNum() {
            return productNum;
        }

        public void setProductNum(int productNum) {
            this.productNum = productNum;
        }

        @Override
        public String toString() {
            return "OrderDTO{" +
                    "productName='" + productName + '\'' +
                    ", productNo='" + productId + '\'' +
                    ", inDate=" + inDate +
                    ", outDate=" + outDate +
                    ", productNum=" + productNum +
                    '}';
        }
    }

