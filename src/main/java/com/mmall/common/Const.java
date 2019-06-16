package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC= Sets.newHashSet("price_desc","price_asc");
    }

    public interface Cart{

        int CHECKED = 1;// certain product in cart is checked

        int UN_CHECKED = 0;// certain product in cart is NOT checked

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";

        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";

    }

    public interface Role{

        int ROLE_CUSTOMER = 0;//normal user

        int ROLE_ADMIN = 1;//admin

    }

    public enum ProductStatusEnum{

        ON_SALE(1,"on sale");

        private String value;

        private int code;

        ProductStatusEnum(int code,String value){

            this.code = code;

            this.value = value;

        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

    }


    public enum OrderStatusEnum{

        CANCELED(0,"Canceled."),
        NO_PAY(10,"Unpaid."),
        PAID(20,"Paid."),
        SHIPPED(40,"Shipped."),
        ORDER_SUCCESS(50,"Order success."),
        ORDER_CLOSE(60,"Order close");

        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue(){
            return value;
        }
        public int getCode(){
            return code;
        }

        public static OrderStatusEnum codeOf(int code){

            for(OrderStatusEnum orderStatusEnum:values()){
                if(orderStatusEnum.getCode()==code){
                    return orderStatusEnum;
                }
            }

            throw new RuntimeException("Can not find the corresponding enum.");
        }

    }


    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum PayPlatform{

        ALIPAY(1,"Zhifubao");

        PayPlatform(int code,String value){
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue(){
            return this.value;
        }
        public int getCode(){
            return this.code;
        }
    }

    public enum PaymentTypeEnum{

        ONLINE_PAY(1,"On line pay.");

        PaymentTypeEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue(){
            return this.value;
        }
        public int getCode(){
            return this.code;
        }

        public static PaymentTypeEnum codeOf(int code){
            for(PaymentTypeEnum paymentTypeEnum:values()){
                if(paymentTypeEnum.getCode()==code){
                    return paymentTypeEnum;
                }
            }

            throw new RuntimeException("Can not find the corresponding enum.");
        }

    }





}
