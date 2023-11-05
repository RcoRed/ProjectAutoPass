package com.github.rcored.project_auto_pass.model.data.abstractions;

import com.github.rcored.project_auto_pass.model.entities.Group;

import java.util.Optional;

public interface AbstractGroupCrudRepository {
    Group create(Group group);

    Optional<Group> readByNameId(String groupNameId);
    //Iterable<Account> read(String name);

    Group update(Group group);

    boolean deleteByNameId(String groupNameId);
}
