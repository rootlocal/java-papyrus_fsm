package ru.rootlocal.papyrus_fsm.fsm;

import org.jetbrains.annotations.NotNull;
import org.springframework.statemachine.StateMachine;

public interface StateMachineService {
    StateMachine<String, String> getStateMachine(String machine_id);

    void setStateMachine(StateMachine<String, String> stateMachine, String machine_id);

    void sendEvent(@NotNull StateMachine<String, String> stateMachine, String event);
}
