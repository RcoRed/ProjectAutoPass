package com.github.rcored.project_auto_pass.model.entities.platforms;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** To initialize all the Platforms you need to call getPLATFORM_MAP() at the beginning of the app (main)
 * @author Marco Martucci
 * @version 0.1.0
 */
@AllArgsConstructor
@ToString
@Getter
@Setter
public abstract class Platform implements AbstractPlatform {
    /** static Map that contains all the platforms, the key is also the id of the Platform */
    @Getter
    private static final Map<Integer,Platform> PLATFORM_MAP = new HashMap<>();
    /** Represent the id of a Platform */
    private int id;
    /** Represent the name of the Platform that will be displayed to the client */
    private String name;

    //Put in the Map all the Platforms
    static {
        PLATFORM_MAP.put(Default.getDEFAULT().getId(), Default.getDEFAULT());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Platform platform = (Platform) o;
        return getId() == platform.getId() && getName().equals(platform.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
