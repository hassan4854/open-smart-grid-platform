/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.cucumber.platform.microgrids.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ReportToNodeMappingService {

    private Map<String, String> reportNodeMap = new HashMap<>();

    public ReportToNodeMappingService() {
        this.reportNodeMap.put("BATTERY1_Measurements", "MMXU1.TotW.q");
        this.reportNodeMap.put("BATTERY1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("BOILER1_Measurements", "MMXU1.TotW.q");
        this.reportNodeMap.put("BOILER1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("CHP1_Measurements", "MMXU1.TotW.q");
        this.reportNodeMap.put("CHP1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("ENGINE1_Measurements", "MMXU1.MaxWPhs.q");
        this.reportNodeMap.put("ENGINE1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("GAS_FURNACE1_Measurements", "TTMP1.TmpSv.q");
        this.reportNodeMap.put("GAS_FURNACE1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("HEAT_BUFFER1_Measurements", "TTMP1.TmpSv.q");
        this.reportNodeMap.put("HEAT_BUFFER1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("HEAT_PUMP1_Measurements", "MMXU1.TotW.q");
        this.reportNodeMap.put("HEAT_PUMP1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("LOAD1_Measurements", "MMXU1.TotW.q");
        this.reportNodeMap.put("LOAD1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("PQ1_Measurements", "MMXU1.Hz.q");
        this.reportNodeMap.put("PQ1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("PV1_Measurements", "MMXU1.TotW.q");
        this.reportNodeMap.put("PV1_Status", "LLN0.Health.q");
        this.reportNodeMap.put("WIND1_Measurements", "MMXU1.TotW.q");
        this.reportNodeMap.put("WIND1_Status", "LLN0.Health.q");
    }

    public String getReportNodeMapKey(final Map<String, String> settings) {
        return this.reportNodeMap.get(settings.get("LogicalDevice") + "_" + settings.get("ReportType"));
    }

}
