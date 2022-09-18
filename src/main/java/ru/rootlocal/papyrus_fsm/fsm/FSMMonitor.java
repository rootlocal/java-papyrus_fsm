package ru.rootlocal.papyrus_fsm.fsm;

import org.jetbrains.annotations.NotNull;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.monitor.AbstractStateMachineMonitor;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component(value = "testStateMachineMonitor")
public class FSMMonitor extends AbstractStateMachineMonitor<String, String> {

    @Override
    public void transition(@NotNull StateMachine<String, String> stateMachine,
                           Transition<String, String> transition, long duration) {
    }

    @Override
    public void action(StateMachine<String, String> stateMachine,
                       Function<StateContext<String, String>, Mono<Void>> action, long duration) {
    }

}