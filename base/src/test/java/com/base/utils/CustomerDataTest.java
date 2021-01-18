package com.base.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yangkuo on 2018-07-27.
 */
public class CustomerDataTest {

    //订单状态
    private CustomerData<String, String, String> orderState;


    @Before
    public void setUp() throws Exception {
        if (orderState == null) {
            orderState = new CustomerData<>();
            orderState.put(0, "wait_buyer_pay", "待付款", "待付款d");
            orderState.put(1, "wait_buyer_confirm_goods", "待收货", "待收货d");
            orderState.put(2, "wait_buyer_evaluate", "待评价", "待评价d");

            orderState.put(3, "wait_seller_confirm_goods", "待确认", "待确认d");
            orderState.put(4, "wait_seller_send_goods", "待发货", "待发货d");

            orderState.put(7, "trade_finished", "交易成功", "交易成功d");
            orderState.put(8, "trade_closed", "交易关闭", "交易关闭d");
            orderState.put(9, "trade_canceled", "交易取消", "交易取消d");
        }
    }

    @After
    public void tearDown() throws Exception {
        orderState = null;
    }


    @Test
    public void getKeyOfId() {
        String key = orderState.getKeyOfId(2);
        assertEquals("wait_buyer_evaluate", key);
    }

    @Test
    public void getKeyAtIndex() {
        String key = orderState.getKeyAtIndex(2);
        assertEquals("wait_buyer_evaluate", key);
    }

    @Test
    public void getKeyOfValue() {
        String key = orderState.getKeyOfValue("待确认");
        assertEquals("wait_seller_confirm_goods", key);
    }

    @Test
    public void getValueOfId() {
        String value = orderState.getValueOfId(2);
        assertEquals("待评价", value);
    }

    @Test
    public void getValueAtIndex() {
        String value = orderState.getValueAtIndex(2);
        assertEquals("待评价", value);
    }

    @Test
    public void getValueOfKey() {
        String value = orderState.getValueOfKey("wait_buyer_evaluate");
        assertEquals("待评价", value);
    }

    @Test
    public void getDesc() {
        String dec = orderState.getDesc(2);
        assertEquals("待评价d", dec);
    }

    @Test
    public void getDesc1() {
        String dec = orderState.getDesc("wait_buyer_evaluate");
        assertEquals("待评价d", dec);
    }

    @Test
    public void getDescAtIndex() {
        String dec = orderState.getDescAtIndex(2);
        assertEquals("待评价d", dec);
    }


    @Test
    public void idAt() {
        int id = orderState.idAt(3);
        assertEquals(3, id);
    }

    @Test
    public void idOfKey() {
        int id = orderState.idOfKey("wait_seller_confirm_goods");
        assertEquals(3, id);
    }

    @Test
    public void idOfValue() {
        int id = orderState.idOfValue("待确认");
        assertEquals(3, id);
    }

    @Test
    public void indexOfValue() {
        int index = orderState.indexOfValue("交易关闭");
        assertEquals(6, index);
    }

    @Test
    public void indexOfKey() {
        int index = orderState.indexOfKey("trade_finished");
        assertEquals(5, index);
    }

    @Test
    public void keyOfValue() {
        String key = orderState.getKeyOfValue("交易取消");
        assertEquals("trade_canceled", key);
    }


}