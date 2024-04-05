package com.boommanpro.statemachine.instance.demo;

import com.alibaba.cola.statemachine.StateMachine;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class OrderSateMachineTest {

    @Test
    public void test() throws IOException {
        StateMachine<OrderStateEnum, OrderEventEnum, OrderStateMachineContext> stateMachine = OrderStateMachineBuilder.build("demo");
        OrderStateEnum orderStateEnum = stateMachine.fireEvent(OrderStateEnum.START, OrderEventEnum.INIT, new OrderStateMachineContext());
        Assert.assertEquals(orderStateEnum, OrderStateEnum.CREATE);
    }
}
