package com.alliander.osgp.shared.infra.jms;

import java.io.Serializable;

public class ProtocolRequestMessage extends RequestMessage {

    private final String domain;
    private final String domainVersion;
    private final String messageType;
    private final String ipAddress;
    private final Serializable messageData;
    private final boolean scheduled;
    private final int retryCount;

    public ProtocolRequestMessage(final String domain, final String domainVersion, final String messageType,
            final String correlationUid, final String organisationIdentification, final String deviceIdentification,
            final String ipAddress, final Serializable request, final int retryCount) {
        this(domain, domainVersion, messageType, correlationUid, organisationIdentification, deviceIdentification,
                ipAddress, request, false, retryCount);
    }

    public ProtocolRequestMessage(final String domain, final String domainVersion, final String messageType,
            final String correlationUid, final String organisationIdentification, final String deviceIdentification,
            final String ipAddress, final Serializable request, final boolean scheduled, final int retryCount) {
        super(correlationUid, organisationIdentification, deviceIdentification, request);

        this.domain = domain;
        this.domainVersion = domainVersion;
        this.messageType = messageType;
        this.ipAddress = ipAddress;

        this.messageData = request;
        this.scheduled = scheduled;
        this.retryCount = retryCount;
    }

    public String getDomain() {
        return this.domain;
    }
    
    public int getRetryCount() {
        return this.retryCount;
    }

    public String getDomainVersion() {
        return this.domainVersion;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public Serializable getMessageData() {
        return this.messageData;
    }

    public boolean isScheduled() {
        return this.scheduled;
    }
}
