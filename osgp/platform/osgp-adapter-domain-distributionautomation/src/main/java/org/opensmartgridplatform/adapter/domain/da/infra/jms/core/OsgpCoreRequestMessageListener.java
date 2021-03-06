/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package org.opensmartgridplatform.adapter.domain.da.infra.jms.core;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.opensmartgridplatform.shared.infra.jms.MessageProcessor;
import org.opensmartgridplatform.shared.infra.jms.MessageProcessorMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Fetches inbound requests from OSGP Core from queue.
 */
@Component(value = "domainDistributionAutomationInboundOsgpCoreRequestsMessageListener")
public class OsgpCoreRequestMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OsgpCoreRequestMessageListener.class);

    @Autowired
    @Qualifier("domainDistributionAutomationInboundOsgpCoreRequestsMessageProcessorMap")
    private MessageProcessorMap messageProcessorMap;

    @Override
    public void onMessage(final Message message) {
        try {
            LOGGER.info("Received message");

            final ObjectMessage objectMessage = (ObjectMessage) message;
            final MessageProcessor processor = this.messageProcessorMap.getMessageProcessor(objectMessage);
            processor.processMessage(objectMessage);

        } catch (final JMSException e) {
            // Can't read message.
            LOGGER.error("Exception: {}, StackTrace: {}", e.getMessage(), e.getStackTrace(), e);
        }
    }
}
