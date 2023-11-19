package com.github.rcored.project_auto_pass.model.entities.platforms;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@ToString
@Getter
@Setter
public abstract class Platform implements AbstractPlatform {
    @Getter
    private static final Map<Integer,Platform> PLATFORM_MAP = new HashMap<>();
    private int id;
    private String name;

    //Carico nella map tutte le Platform
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
