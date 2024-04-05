package com.boommanpro.statemachine.instance.demo;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.alibaba.fastjson2.JSON;
import com.boommanpro.statemachine.templatefactory.config.TransitionConfigModel;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

public class OrderStateMachineBuilder {

    private static final String configPath = "instance/demo/orderTransition.json";


    public static StateMachine<OrderStateEnum, OrderEventEnum, OrderStateMachineContext> build(String machineId) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(configPath);
        List<TransitionConfigModel> transitionConfigModels = JSON.parseArray(IOUtils.toString(classPathResource.getInputStream()), TransitionConfigModel.class);
        StateMachineBuilder<OrderStateEnum, OrderEventEnum, OrderStateMachineContext> builder = StateMachineBuilderFactory.create();
        for (TransitionConfigModel model : transitionConfigModels) {
            builder.externalTransition()
                    .from(OrderStateEnum.fromCode(model.getFrom()))
                    .to(OrderStateEnum.fromCode(model.getTo()))
                    .on(OrderEventEnum.fromCode(model.getEvent()));
        }
        return builder.build(machineId);

    }
}
