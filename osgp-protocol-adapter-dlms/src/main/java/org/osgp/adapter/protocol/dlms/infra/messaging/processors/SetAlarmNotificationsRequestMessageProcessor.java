/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.osgp.adapter.protocol.dlms.infra.messaging.processors;

import java.io.Serializable;

import org.osgp.adapter.protocol.dlms.application.services.ConfigurationService;
import org.osgp.adapter.protocol.dlms.exceptions.ProtocolAdapterException;
import org.osgp.adapter.protocol.dlms.infra.messaging.DeviceRequestMessageProcessor;
import org.osgp.adapter.protocol.dlms.infra.messaging.DeviceRequestMessageType;
import org.osgp.adapter.protocol.dlms.infra.messaging.DlmsDeviceMessageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alliander.osgp.dto.valueobjects.smartmetering.AlarmNotifications;
import com.alliander.osgp.shared.exceptionhandling.OsgpException;

/**
 * Class for processing set alarm notifications request messages
 */
@Component("dlmsSetAlarmNotificationsRequestMessageProcessor")
public class SetAlarmNotificationsRequestMessageProcessor extends DeviceRequestMessageProcessor {

    @Autowired
    private ConfigurationService configurationService;

    public SetAlarmNotificationsRequestMessageProcessor() {
        super(DeviceRequestMessageType.SET_ALARM_NOTIFICATIONS);
    }

    @Override
    protected Serializable handleMessage(final DlmsDeviceMessageMetadata messageMetadata,
            final Serializable requestObject) throws OsgpException, ProtocolAdapterException {
        final AlarmNotifications alarmNotifications = (AlarmNotifications) requestObject;

        this.configurationService.setAlarmNotifications(messageMetadata, alarmNotifications);

        return null;
    }
}
