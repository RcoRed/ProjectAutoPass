package com.github.rcored.project_auto_pass.model.entities.platforms;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

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
}
