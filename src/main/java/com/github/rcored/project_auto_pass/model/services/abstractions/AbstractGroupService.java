package com.github.rcored.project_auto_pass.model.services.abstractions;

import com.github.rcored.project_auto_pass.model.data.exceptions.BusinessLogicException;
import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.data.exceptions.EntityNotFoundException;
import com.github.rcored.project_auto_pass.model.entities.Group;

import java.util.Optional;

public interface AbstractGroupService {

    Group createGroup(Group group) throws DataException, BusinessLogicException;
    Optional<Group> readGroupByNameId(String groupNameId) throws EntityNotFoundException, DataException;
    Group updateGroup(Group oldGroup, Group newGroup) throws BusinessLogicException, DataException;
    boolean deleteGroupByNameId(String groupNameId);
    Iterable<Group> findAllGroup() throws EntityNotFoundException, DataException;
    boolean groupNameFileExist(String groupNameId);
}
