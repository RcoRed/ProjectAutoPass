package com.github.rcored.project_auto_pass.model.services;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractGroupCrudRepository;
import com.github.rcored.project_auto_pass.model.entities.Group;

public class GroupCrudService {

    private AbstractGroupCrudRepository groupRepo;

    public GroupCrudService(AbstractGroupCrudRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    public Group createGroup(Group group){
        return groupRepo.create(group);
    }

    public Iterable<Group> findAllGroup(){
        return groupRepo.findAllGroup();
    }

}
