package com.github.rcored.project_auto_pass.model.entities.platforms;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractPlatform;
import lombok.*;

@AllArgsConstructor
@ToString
@Getter
@Setter
public abstract class Platform implements AbstractPlatform {
    private int id;
    private String name;
}
