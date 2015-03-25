package com.alliander.osgp.adapter.domain.publiclighting.infra.jms.ws;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alliander.osgp.adapter.domain.publiclighting.infra.jms.core.OsgpCoreRequestMessageSender;
import com.alliander.osgp.domain.core.valueobjects.DeviceFunction;
import com.alliander.osgp.shared.infra.jms.MessageProcessor;
import com.alliander.osgp.shared.infra.jms.ResponseMessage;
import com.alliander.osgp.shared.infra.jms.ResponseMessageResultType;

/**
 * Base class for MessageProcessor implementations. Each MessageProcessor
 * implementation should be annotated with @Component. Further the MessageType
 * the MessageProcessor implementation can process should be passed in at
 * construction. The Singleton instance is added to the HashMap of
 * MessageProcessors after dependency injection has completed.
 * 
 * @author CGI
 * 
 */
public abstract class WebServiceRequestMessageProcessor implements MessageProcessor {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceRequestMessageProcessor.class);

    /**
     * This is the message sender needed for the message processor
     * implementations to handle the forwarding of messages to OSGP-CORE.
     */
    @Qualifier("domainPublicLightingOutgoingOsgpCoreRequestMessageSender")
    @Autowired
    protected OsgpCoreRequestMessageSender coreRequestMessageSender;

    /**
     * This is the message sender needed for the message processor
     * implementation to handle an error.
     */
    @Autowired
    protected WebServiceResponseMessageSender webServiceResponseMessageSender;

    /**
     * The hash map of message processor instances.
     */
    @Autowired
    protected WebServiceRequestMessageProcessorMap webServiceRequestMessageProcessorMap;

    /**
     * The message type that a message processor implementation can handle.
     */
    protected DeviceFunction deviceFunction;

    /**
     * Construct a message processor instance by passing in the message type.
     * 
     * @param deviceFunction
     *            The message type a message processor can handle.
     */
    protected WebServiceRequestMessageProcessor(final DeviceFunction deviceFunction) {
        this.deviceFunction = deviceFunction;
    }

    /**
     * Initialization function executed after dependency injection has finished.
     * The MessageProcessor Singleton is added to the HashMap of
     * MessageProcessors. The key for the HashMap is the integer value of the
     * enumeration member.
     */
    @PostConstruct
    public void init() {
        this.webServiceRequestMessageProcessorMap.addMessageProcessor(this.deviceFunction.ordinal(),
                this.deviceFunction.name(), this);
    }

    /**
     * In case of an error, this function can be used to send a response
     * containing the exception to the web-service-adapter.
     * 
     * @param e
     *            The exception.
     * @param correlationUid
     *            The correlation UID.
     * @param organisationIdentification
     *            The organisation identification.
     * @param deviceIdentification
     *            The device identification.
     * @param messageType
     *            The message type.
     */
    protected void handleError(final Exception e, final String correlationUid, final String organisationIdentification,
            final String deviceIdentification, final String messageType) {
        LOGGER.info("handeling error: {} for message type: {}", e.getMessage(), messageType);

        this.webServiceResponseMessageSender.send(new ResponseMessage(correlationUid, organisationIdentification,
                deviceIdentification, ResponseMessageResultType.NOT_OK, e.getMessage(), e));
    }
}
