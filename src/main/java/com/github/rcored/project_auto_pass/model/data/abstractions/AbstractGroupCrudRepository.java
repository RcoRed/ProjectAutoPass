package com.github.rcored.project_auto_pass.model.data.abstractions;

import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.data.exceptions.EntityNotFoundException;
import com.github.rcored.project_auto_pass.model.entities.Group;

import java.util.Optional;

public interface AbstractGroupCrudRepository {
    Group create(Group group) throws DataException;
    Optional<Group> readByNameId(String groupNameId) throws EntityNotFoundException, DataException;
    Group update(Group newGroup) throws DataException;
    boolean deleteByNameId(String groupNameId);     //Doesn't throw exceptions due to boolean return
    boolean groupNameFileExist(String groupNameId);
}
