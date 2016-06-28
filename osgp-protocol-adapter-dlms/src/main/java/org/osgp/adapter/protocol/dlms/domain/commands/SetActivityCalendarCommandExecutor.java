/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.osgp.adapter.protocol.dlms.domain.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmuc.jdlms.AccessResultCode;
import org.openmuc.jdlms.AttributeAddress;
import org.openmuc.jdlms.DlmsConnection;
import org.openmuc.jdlms.ObisCode;
import org.openmuc.jdlms.datatypes.DataObject;
import org.osgp.adapter.protocol.dlms.application.mapping.ConfigurationMapper;
import org.osgp.adapter.protocol.dlms.domain.entities.DlmsDevice;
import org.osgp.adapter.protocol.dlms.exceptions.ProtocolAdapterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alliander.osgp.dto.valueobjects.smartmetering.ActivityCalendarDto;
import com.alliander.osgp.dto.valueobjects.smartmetering.DayProfileDto;
import com.alliander.osgp.dto.valueobjects.smartmetering.SeasonProfileDto;
import com.alliander.osgp.dto.valueobjects.smartmetering.WeekProfileDto;

@Component()
public class SetActivityCalendarCommandExecutor implements CommandExecutor<ActivityCalendarDto, AccessResultCode> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetActivityCalendarCommandExecutor.class);

    private static final int CLASS_ID = 20;
    private static final ObisCode OBIS_CODE = new ObisCode("0.0.13.0.0.255");
    private static final int ATTRIBUTE_ID_CALENDAR_NAME_PASSIVE = 6;
    private static final int ATTRIBUTE_ID_SEASON_PROFILE_PASSIVE = 7;
    private static final int ATTRIBUTE_ID_WEEK_PROFILE_TABLE_PASSIVE = 8;
    private static final int ATTRIBUTE_ID_DAY_PROFILE_TABLE_PASSIVE = 9;

    @Autowired
    private ConfigurationMapper configurationMapper;

    @Autowired
    private DlmsHelperService dlmsHelperService;

    @Override
    public AccessResultCode execute(final DlmsConnection conn, final DlmsDevice device,
            final ActivityCalendarDto activityCalendar) throws ProtocolAdapterException {
        LOGGER.debug("SetActivityCalendarCommandExecutor.execute {} called", activityCalendar.getCalendarName());

        final List<SeasonProfileDto> seasonProfileList = activityCalendar.getSeasonProfileList();
        final Set<WeekProfileDto> weekProfileSet = this.getWeekProfileSet(seasonProfileList);
        final Set<DayProfileDto> dayProfileSet = this.getDayProfileSet(weekProfileSet);

        new DataObjectAttrExecutors("SetActivityCalendar").addExecutor(this.getCalendarNameExecutor(activityCalendar))
                .addExecutor(this.getSeasonProfileExecutor(seasonProfileList))
                .addExecutor(this.getWeekProfileTableExecutor(weekProfileSet))
                .addExecutor(this.getDayProfileTablePassiveExecutor(dayProfileSet)).execute(conn);

        LOGGER.info("Finished calling conn.set");

        return AccessResultCode.SUCCESS;
    }

    private DataObjectAttrExecutor getCalendarNameExecutor(final ActivityCalendarDto activityCalendar) {
        final AttributeAddress calendarNamePassive = new AttributeAddress(CLASS_ID, OBIS_CODE,
                ATTRIBUTE_ID_CALENDAR_NAME_PASSIVE);
        final DataObject value = DataObject.newOctetStringData(activityCalendar.getCalendarName().getBytes());
        return new DataObjectAttrExecutor("CALENDARNAME", calendarNamePassive, value, CLASS_ID, OBIS_CODE,
                ATTRIBUTE_ID_CALENDAR_NAME_PASSIVE);
    }

    private DataObjectAttrExecutor getDayProfileTablePassiveExecutor(final Set<DayProfileDto> dayProfileSet) {
        final AttributeAddress dayProfileTablePassive = new AttributeAddress(CLASS_ID, OBIS_CODE,
                ATTRIBUTE_ID_DAY_PROFILE_TABLE_PASSIVE);
        final DataObject dayArray = DataObject.newArrayData(this.configurationMapper.mapAsList(dayProfileSet,
                DataObject.class));

        LOGGER.info("DayProfileTablePassive to set is: {}", this.dlmsHelperService.getDebugInfo(dayArray));

        return new DataObjectAttrExecutor("DAYS", dayProfileTablePassive, dayArray, CLASS_ID, OBIS_CODE,
                ATTRIBUTE_ID_DAY_PROFILE_TABLE_PASSIVE);
    }

    /**
     * get all day profiles from all the week profiles
     *
     * @param weekProfileSet
     * @return
     */
    private Set<DayProfileDto> getDayProfileSet(final Set<WeekProfileDto> weekProfileSet) {
        final Set<DayProfileDto> dayProfileHashSet = new HashSet<>();

        for (final WeekProfileDto weekProfile : weekProfileSet) {
            dayProfileHashSet.addAll(weekProfile.getAllDaysAsList());
        }

        return dayProfileHashSet;
    }

    private DataObjectAttrExecutor getWeekProfileTableExecutor(final Set<WeekProfileDto> weekProfileSet) {

        final AttributeAddress weekProfileTablePassive = new AttributeAddress(CLASS_ID, OBIS_CODE,
                ATTRIBUTE_ID_WEEK_PROFILE_TABLE_PASSIVE);
        final DataObject weekArray = DataObject.newArrayData(this.configurationMapper.mapAsList(weekProfileSet,
                DataObject.class));

        LOGGER.info("WeekProfileTablePassive to set is: {}", this.dlmsHelperService.getDebugInfo(weekArray));

        return new DataObjectAttrExecutor("WEEKS", weekProfileTablePassive, weekArray, CLASS_ID, OBIS_CODE,
                ATTRIBUTE_ID_WEEK_PROFILE_TABLE_PASSIVE);
    }

    private Set<WeekProfileDto> getWeekProfileSet(final List<SeasonProfileDto> seasonProfileList) {
        // Use HashSet to ensure that unique WeekProfiles are returned. For
        // there can be duplicates.
        final Set<WeekProfileDto> weekProfileSet = new HashSet<>();

        for (final SeasonProfileDto seasonProfile : seasonProfileList) {
            weekProfileSet.add(seasonProfile.getWeekProfile());
        }
        return weekProfileSet;
    }

    private DataObjectAttrExecutor getSeasonProfileExecutor(final List<SeasonProfileDto> seasonProfileList) {

        final AttributeAddress seasonProfilePassive = new AttributeAddress(CLASS_ID, OBIS_CODE,
                ATTRIBUTE_ID_SEASON_PROFILE_PASSIVE);
        final DataObject seasonsArray = DataObject.newArrayData(this.configurationMapper.mapAsList(seasonProfileList,
                DataObject.class));

        LOGGER.info("SeasonProfilePassive to set is: {}", this.dlmsHelperService.getDebugInfo(seasonsArray));

        return new DataObjectAttrExecutor("SEASONS", seasonProfilePassive, seasonsArray, CLASS_ID, OBIS_CODE,
                ATTRIBUTE_ID_SEASON_PROFILE_PASSIVE);
    }
}