package ru.rootlocal.papyrus_fsm.fsm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class UmlConfig {
    @Value("${application.papyrus.uml}")
    private String uml;


    public String getUml() {
        return uml;
    }
}
