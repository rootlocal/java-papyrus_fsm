package ru.rootlocal.papyrus_fsm.fsm;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnableWithStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.monitor.StateMachineMonitor;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.uml.UmlStateMachineModelFactory;
import ru.rootlocal.papyrus_fsm.fsm.actions.*;

import java.util.Optional;

@Configuration
@EnableStateMachineFactory(name = "stateMachineFactory")
@EnableWithStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {
    private final Logger log = LoggerFactory.getLogger(StateMachineConfig.class);
    private final FSMMonitor fsmMonitor;
    private final S1Action s1Action;
    private final S2Action s2Action;
    private final UmlConfig umlConfig;


    @Autowired
    public StateMachineConfig(
            FSMMonitor fsmMonitor,
            S1Action s1Action, S2Action s2Action, UmlConfig umlConfig) {
        this.fsmMonitor = fsmMonitor;
        this.s1Action = s1Action;
        this.s2Action = s2Action;
        this.umlConfig = umlConfig;
    }

    @Override
    public void configure(@NotNull StateMachineModelConfigurer<String, String> model) throws Exception {
        model.withModel().factory(modelFactory());
    }

    @Override
    public void configure(final @NotNull StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(botStateMachineListener())
                .and()
                .withMonitoring()
                .monitor(stateMachineMonitor());
    }

    @Override
    public void configure(final @NotNull StateMachineStateConfigurer<String, String> states) throws Exception {
        states.withStates().initial("S1")
                .state("S1", s1Action)
                .state("S2", s2Action);
    }

    @Bean
    public StateMachineModelFactory<String, String> modelFactory() {
        return new UmlStateMachineModelFactory(umlConfig.getUml());
    }

    @Bean(name = "stateMachineMonitor")
    public StateMachineMonitor<String, String> stateMachineMonitor() {
        return fsmMonitor;
    }

    @Bean(name = "botStateMachineListener")
    public StateMachineListener<String, String> botStateMachineListener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void transition(Transition<String, String> transition) {
                log.warn("transition move from:{} to:{}",
                        ofNullableState(transition.getSource()),
                        ofNullableState(transition.getTarget()));
            }

            @Override
            public void eventNotAccepted(Message<String> event) {
                log.error("event not accepted: {}", event);
            }

            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                // state machine Changed action
                //log.warn("State Changed from:{} to:{}", ofNullableState(from), ofNullableState(to));
            }

            private Object ofNullableState(State<String, String> s) {
                return Optional.ofNullable(s)
                        .map(State::getId)
                        .orElse(null);
            }

        };
    }
}
