package com.sequenceiq.cloudbreak.core.flow.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.core.CloudbreakException;
import com.sequenceiq.cloudbreak.core.flow.AbstractFlowHandler;
import com.sequenceiq.cloudbreak.core.flow.FlowHandler;
import com.sequenceiq.cloudbreak.core.flow.context.DefaultFlowContext;
import com.sequenceiq.cloudbreak.core.flow.context.FlowContext;

import reactor.bus.Event;
@Component
public class StackStopRequestedHandler extends AbstractFlowHandler<DefaultFlowContext> implements FlowHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackStopRequestedHandler.class);

    @Override
    protected Object execute(Event<DefaultFlowContext> event) throws CloudbreakException {
        LOGGER.info("execute() for phase: {}", event.getKey());
        FlowContext context = getFlowFacade().stopStackRequested(event.getData());
        LOGGER.info("Stack stop requested handled. Context: {}", context);
        return context;
    }
}
