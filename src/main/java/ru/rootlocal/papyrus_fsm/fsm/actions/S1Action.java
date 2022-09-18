package ru.rootlocal.papyrus_fsm.fsm.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component(value = "S1Action")
public class S1Action extends FSMAction<String, String> {
    private final Logger log = LoggerFactory.getLogger(S1Action.class);


    @Override
    public void execute(StateContext<String, String> context) {
        log.warn("execute: S1Action");
    }
}
