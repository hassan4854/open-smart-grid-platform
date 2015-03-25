package com.alliander.osgp.domain.core.valueobjects;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.alliander.osgp.domain.core.validation.MapKeyRange;
import com.alliander.osgp.domain.core.validation.MapValueRange;
import com.alliander.osgp.domain.core.validation.NumberOfLightsAndIndexAddressMap;

@NumberOfLightsAndIndexAddressMap
public class DaliConfiguration implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = -4779233927956697006L;

    @Min(0)
    @Max(4)
    private final Integer numberOfLights;

    @Size(min = 0, max = 4)
    @MapKeyRange(min = 1, max = 4)
    @MapValueRange(min = 1, max = 255)
    private final Map<Integer, Integer> indexAddressMap;

    public DaliConfiguration(final Integer numberOfLights, final Map<Integer, Integer> indexAddressMap) {
        this.numberOfLights = numberOfLights;
        // Shallow copy and unmodifiable version of the map to prevent external
        // changes to the map
        this.indexAddressMap = Collections.unmodifiableMap(new HashMap<>(indexAddressMap));
    }

    public Integer getNumberOfLights() {
        return this.numberOfLights;
    }

    public Map<Integer, Integer> getIndexAddressMap() {
        // Returns the property directly as it is already made unmodifiable in
        // the constructor.
        return this.indexAddressMap;
    }
}
