package com.software.PScheduling.dto;

import java.util.Date;

public class OrderDTO {


        private String productName;//产品名字
        private String productNo;//产品编号
        private Date inDate;//当前日期
        private Date outDate;//交货日期
        private int productNum;//生产数量

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
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
                    ", productNo='" + productNo + '\'' +
                    ", inDate=" + inDate +
                    ", outDate=" + outDate +
                    ", productNum=" + productNum +
                    '}';
        }
    }

