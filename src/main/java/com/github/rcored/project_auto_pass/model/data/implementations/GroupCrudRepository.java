package com.github.rcored.project_auto_pass.model.data.implementations;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractGroupCrudRepository;
import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.data.exceptions.EntityNotFoundException;
import com.github.rcored.project_auto_pass.model.entities.Group;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

import static com.github.rcored.project_auto_pass.model.data.Constants.*;

@AllArgsConstructor
@Setter
@Getter
public class GroupCrudRepository implements AbstractGroupCrudRepository {
    private Gson gson;
    @Override
    public Group create(Group group) throws DataException {
        try (FileOutputStream output = new FileOutputStream(GROUP_DIR_PATH_ + group.getGroupNameId() + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output)){
            //writing the file
            gson.toJson(group,pw);
            return group;
        }catch (IOException e) {
            throw new DataException("Error creating the GROUP file: " + group.getGroupNameId() + e.getMessage(),e.getCause());
        }
    }

    @Override
    public Optional<Group> readByNameId(String groupNameId) throws EntityNotFoundException, DataException{
        try (Reader reader = new FileReader(GROUP_DIR_PATH_ + groupNameId + JSON_TYPE)) {
            //read the group object, if null return optional empty
            return Optional.ofNullable(gson.fromJson(reader, Group.class));
        } catch (FileNotFoundException e) {
            throw new EntityNotFoundException("Unable to read the GROUP file: " + groupNameId + e.getMessage(), e.getCause());
        } catch (IOException e) {
            throw new DataException("Error reading the GROUP file: " +groupNameId + e.getMessage(),e.getCause());
        }
    }

    @Override
    public Group update(Group newGroup) throws DataException{
        try (FileOutputStream output = new FileOutputStream(GROUP_DIR_PATH_ + newGroup.getGroupNameId() + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output)){
            //writing the file
            gson.toJson(newGroup,pw);
            return newGroup;
        } catch (IOException e) {
            throw new DataException("Error updating the GROUP file: " + newGroup.getGroupNameId() + e.getMessage(),e.getCause());
        }
    }

    @Override
    public boolean deleteByNameId(String groupNameId){
        return new File(GROUP_DIR_PATH_ + groupNameId + JSON_TYPE).delete();
    }

    public boolean groupNameFileExist(String groupNameId){
        //read the folder and then all the files in it
        File[] files = new File(GROUP_DIR_PATH).listFiles();
        if (files != null){
            //finding if file name match the current group name
            return Arrays.stream(files).anyMatch(f -> f.getName().equals(groupNameId + JSON_TYPE));
        }
        return false;
    }
}
