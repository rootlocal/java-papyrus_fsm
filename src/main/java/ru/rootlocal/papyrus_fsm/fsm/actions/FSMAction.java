package ru.rootlocal.papyrus_fsm.fsm.actions;

import org.springframework.statemachine.action.Action;

public abstract class FSMAction<S, E> implements Action<S, E>, FSMActionInterface<S, E> {
}