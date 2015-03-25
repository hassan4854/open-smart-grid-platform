package com.alliander.osgp.adapter.ws.tariffswitching.infra.jms;

import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;

import com.alliander.osgp.shared.infra.jms.BaseResponseMessageFinder;

/**
 * Class for retrieving response messages from the tariff switching responses
 * queue by correlation UID.
 * 
 * @author CGI
 * 
 */
public class TariffSwitchingResponseMessageFinder extends BaseResponseMessageFinder {

    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TariffSwitchingResponseMessageFinder.class);

    /**
     * Autowired JMS template for OSGP domain tariff switching responses queue.
     */
    @Autowired
    @Qualifier("wsTariffSwitchingIncomingResponsesJmsTemplate")
    private JmsTemplate tariffSwitchingResponsesJmsTemplate;

    @Override
    protected ObjectMessage receiveObjectMessage(final String correlationUid) {
        LOGGER.info("Trying to find message with correlationUID: {}", correlationUid);

        return (ObjectMessage) this.tariffSwitchingResponsesJmsTemplate.receiveSelected(this
                .getJmsCorrelationId(correlationUid));
    }
}
