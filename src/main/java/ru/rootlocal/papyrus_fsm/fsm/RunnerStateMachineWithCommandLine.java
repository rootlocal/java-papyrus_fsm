package ru.rootlocal.papyrus_fsm.fsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

@Component
public class RunnerStateMachineWithCommandLine implements CommandLineRunner {
    private final StateMachineFactory<String, String> stateMachineFactory;
    Logger log = LoggerFactory.getLogger(RunnerStateMachineWithCommandLine.class);


    @Autowired
    public RunnerStateMachineWithCommandLine(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
            StateMachineFactory<String, String> stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }

    @Override
    public void run(String... args) {
        StateMachine<String, String> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.startReactively().subscribe();
        log.info("machine: {} State: {}", stateMachine.getUuid(), stateMachine.getState().getId());
    }
}
