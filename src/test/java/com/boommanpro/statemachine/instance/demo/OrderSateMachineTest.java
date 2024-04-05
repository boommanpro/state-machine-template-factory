package com.boommanpro.statemachine.instance.demo;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.exception.TransitionFailException;
import com.alibaba.cola.test.StateMachineTest;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class OrderSateMachineTest {

    @Test
    public void test() throws IOException {
        StateMachine<OrderStateEnum, OrderEventEnum, OrderStateMachineContext> stateMachine = OrderStateMachineBuilder.build("demo");
        OrderStateEnum orderStateEnum = stateMachine.fireEvent(OrderStateEnum.START, OrderEventEnum.INIT, new OrderStateMachineContext());
        Assert.assertEquals(orderStateEnum, OrderStateEnum.CREATE);
        System.out.println(stateMachine.generatePlantUML());
        Assert.assertThrows(TransitionFailException.class,
                () -> stateMachine.fireEvent(OrderStateEnum.END, OrderEventEnum.INIT, new OrderStateMachineContext()));
    }
}
