package ru.rootlocal.papyrus_fsm.fsm;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import java.util.HashMap;

class MemoryPersister<S, E, T> implements StateMachinePersist<S, E, T> {

    private final HashMap<T, StateMachineContext<S, E>> storage = new HashMap<>();

    @Override
    public void write(final StateMachineContext<S, E> context, T contextObj) {
        storage.put(contextObj, context);
    }

    @Override
    public StateMachineContext<S, E> read(final T contextObj) {
        return storage.get(contextObj);
    }
}
