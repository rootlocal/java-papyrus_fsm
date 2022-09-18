package ru.rootlocal.papyrus_fsm.fsm;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StateMachineServiceImpl implements StateMachineService {
    private final static Logger log = LoggerFactory.getLogger(StateMachineServiceImpl.class);
    private final StateMachineFactory<String, String> stateMachineFactory;
    private final StateMachinePersister<String, String, String> persister;


    @Autowired
    public StateMachineServiceImpl(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
            StateMachineFactory<String, String> stateMachineFactory,
            StateMachinePersister<String, String, String> persister) {
        this.stateMachineFactory = stateMachineFactory;
        this.persister = persister;
    }

    public synchronized StateMachine<String, String> getStateMachine(String machine_id) {

        try {
            return persister.restore(stateMachineFactory.getStateMachine(), machine_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return null;
    }

    public synchronized void setStateMachine(StateMachine<String, String> stateMachine, String machine_id) {

        try {
            persister.persist(stateMachine, machine_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public synchronized void sendEvent(@NotNull StateMachine<String, String> stateMachine, String event) {
        //Message<String> message = new GenericMessage<>(event);
        Message<String> message = MessageBuilder.withPayload(event).build();
        stateMachine.sendEvent(Mono.just(message)).subscribe(res -> log.info(res.toString()), e -> log.error(e.getMessage()));
    }


}
