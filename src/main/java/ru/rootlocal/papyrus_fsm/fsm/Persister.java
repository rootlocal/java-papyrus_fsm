package ru.rootlocal.papyrus_fsm.fsm;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class Persister {
    public final static String DEFAULT_PERSIST = "inMemoryPersist";
    public final static String MEMORY_PERSIST = "inMemoryPersist";

    @Bean(name = MEMORY_PERSIST)
    public StateMachinePersist<String, String, String> inMemoryPersist() {
        return new MemoryPersister<>();
    }

    @Bean
    public StateMachinePersister<String, String, String> memoryPersist(
            @Qualifier(DEFAULT_PERSIST) StateMachinePersist<String, String, String> defaultPersist) {
        return new DefaultStateMachinePersister<>(defaultPersist);
    }

}
