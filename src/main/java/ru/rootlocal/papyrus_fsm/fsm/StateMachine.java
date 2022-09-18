package ru.rootlocal.papyrus_fsm.fsm;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnableWithStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.monitor.StateMachineMonitor;
import org.springframework.statemachine.uml.UmlStateMachineModelFactory;

@Configuration
@EnableStateMachineFactory(name = "stateMachineFactory")
@EnableWithStateMachine
public class StateMachine extends StateMachineConfigurerAdapter<String, String> {
    private final FSMMonitor fsmMonitor;

    @Autowired
    public StateMachine(FSMMonitor fsmMonitor) {
        this.fsmMonitor = fsmMonitor;
    }

    @Override
    public void configure(@NotNull StateMachineModelConfigurer<String, String> model) throws Exception {
        model.withModel().factory(modelFactory());
    }

    @Bean
    public StateMachineModelFactory<String, String> modelFactory() {
        String location = "classpath:ru/rootlocal/papyrus_fsm/machine.uml";
        return new UmlStateMachineModelFactory(location);
    }

    @Bean(name = "stateMachineMonitor")
    public StateMachineMonitor<String, String> stateMachineMonitor() {
        return fsmMonitor;
    }
}
