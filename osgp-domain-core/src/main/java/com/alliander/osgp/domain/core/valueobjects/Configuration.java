package com.alliander.osgp.domain.core.valueobjects;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.alliander.osgp.domain.core.validation.LightTypeAndConfiguration;
import com.alliander.osgp.domain.core.validation.LongTermIntervalAndLongTermIntervalType;
import com.alliander.osgp.domain.core.validation.ShortTermHistoryIntervalMinutes;

@LightTypeAndConfiguration
@LongTermIntervalAndLongTermIntervalType
@ShortTermHistoryIntervalMinutes
public class Configuration implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 8359276160483972289L;

    private final LightType lightType;

    @Valid
    private final DaliConfiguration daliConfiguration;

    @Valid
    private final RelayConfiguration relayConfiguration;

    @Min(0)
    private final Integer shortTermHistoryIntervalMinutes;

    @Min(0)
    private final Integer longTermHistoryInterval;

    private final LongTermIntervalType longTermHistoryIntervalType;

    private final LinkType preferredLinkType;

    private final MeterType meterType;

    public Configuration(final LightType lightType, final DaliConfiguration daliConfiguration,
            final RelayConfiguration relayConfiguration, final Integer shortTermHistoryIntervalMinutes,
            final LinkType preferredLinkType, final MeterType meterType, final Integer longTermHistoryInterval,
            final LongTermIntervalType longTermHistoryIntervalType) {
        this.lightType = lightType;
        this.daliConfiguration = daliConfiguration;
        this.relayConfiguration = relayConfiguration;
        this.shortTermHistoryIntervalMinutes = shortTermHistoryIntervalMinutes;
        this.preferredLinkType = preferredLinkType;
        this.meterType = meterType;
        this.longTermHistoryInterval = longTermHistoryInterval;
        this.longTermHistoryIntervalType = longTermHistoryIntervalType;
    }

    public MeterType getMeterType() {
        return this.meterType;
    }

    public LightType getLightType() {
        return this.lightType;
    }

    public DaliConfiguration getDaliConfiguration() {
        return this.daliConfiguration;
    }

    public RelayConfiguration getRelayConfiguration() {
        return this.relayConfiguration;
    }

    public Integer getShortTermHistoryIntervalMinutes() {
        return this.shortTermHistoryIntervalMinutes;
    }

    public LinkType getPreferredLinkType() {
        return this.preferredLinkType;
    }

    public Integer getLongTermHistoryInterval() {
        return this.longTermHistoryInterval;
    }

    public LongTermIntervalType getLongTermHistoryIntervalType() {
        return this.longTermHistoryIntervalType;
    }
}
