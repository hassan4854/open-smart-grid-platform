package com.alliander.osgp.adapter.domain.core.infra.jms.core.messageprocessors;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alliander.osgp.adapter.domain.core.application.services.DefaultDeviceResponseService;
import com.alliander.osgp.adapter.domain.core.infra.jms.core.OsgpCoreResponseMessageProcessor;
import com.alliander.osgp.domain.core.valueobjects.DeviceFunction;
import com.alliander.osgp.shared.infra.jms.Constants;
import com.alliander.osgp.shared.infra.jms.ResponseMessage;
import com.alliander.osgp.shared.infra.jms.ResponseMessageResultType;

/**
 * Class for processing common default response messages
 * 
 * @author CGI
 * 
 */
@Component("domainCoreCommonDefaultResponseMessageProcessor")
public class CommonDefaultResponseMessageProcessor extends OsgpCoreResponseMessageProcessor {
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDefaultResponseMessageProcessor.class);

    @Autowired
    @Qualifier("domainCoreDefaultDeviceResponseService")
    private DefaultDeviceResponseService defaultDeviceResponseService;

    protected CommonDefaultResponseMessageProcessor() {
        super(DeviceFunction.SET_CONFIGURATION);
        this.addMessageType(DeviceFunction.UPDATE_FIRMWARE);
        this.addMessageType(DeviceFunction.SET_REBOOT);
        this.addMessageType(DeviceFunction.SET_EVENT_NOTIFICATIONS);
        this.addMessageType(DeviceFunction.START_SELF_TEST);
        this.addMessageType(DeviceFunction.STOP_SELF_TEST);
    }

    @Override
    public void processMessage(final ObjectMessage message) throws JMSException {
        LOGGER.debug("Processing common default response message");

        String correlationUid = null;
        String messageType = null;
        String organisationIdentification = null;
        String deviceIdentification = null;

        ResponseMessage responseMessage = null;
        ResponseMessageResultType responseMessageResultType = null;
        String description = null;

        try {
            correlationUid = message.getJMSCorrelationID();
            messageType = message.getJMSType();
            organisationIdentification = message.getStringProperty(Constants.ORGANISATION_IDENTIFICATION);
            deviceIdentification = message.getStringProperty(Constants.DEVICE_IDENTIFICATION);

            responseMessage = (ResponseMessage) message.getObject();
            responseMessageResultType = responseMessage.getResult();
            description = responseMessage.getDescription();
        } catch (final JMSException e) {
            LOGGER.error("UNRECOVERABLE ERROR, unable to read ObjectMessage instance, giving up.", e);
            LOGGER.debug("correlationUid: {}", correlationUid);
            LOGGER.debug("messageType: {}", messageType);
            LOGGER.debug("organisationIdentification: {}", organisationIdentification);
            LOGGER.debug("deviceIdentification: {}", deviceIdentification);
            LOGGER.debug("responseMessageResultType: {}", responseMessageResultType);
            LOGGER.debug("deviceIdentification: {}", deviceIdentification);
            LOGGER.debug("description: {}", description);
            return;
        }

        try {
            LOGGER.info("Calling application service function to handle response: {}", messageType);

            this.defaultDeviceResponseService.handleDefaultDeviceResponse(deviceIdentification,
                    organisationIdentification, correlationUid, messageType, responseMessageResultType, description);

        } catch (final Exception e) {
            this.handleError(e, correlationUid, organisationIdentification, deviceIdentification, messageType);
        }
    }
}
