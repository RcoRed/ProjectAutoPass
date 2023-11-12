package com.github.rcored.project_auto_pass.model.data.implementations;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractGroupCrudRepository;
import com.github.rcored.project_auto_pass.model.entities.Group;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Setter
@Getter
public class GroupCrudRepository implements AbstractGroupCrudRepository {
    private static final String JSON_TYPE = ".json";
    private static final String JSON_DIR_PATH = "groups/";
    private Gson gson;
    @Override
    public Group create(Group group) {
        //check if the file already exist
        if (groupNameFileExist(group.getGroupNameId())){
            throw new RuntimeException("Il file con groupNameId: " + group.getGroupNameId() + " esiste già. Non è possibile ricrearlo.");
        }
        try (FileOutputStream output = new FileOutputStream(JSON_DIR_PATH + group.getGroupNameId() + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output)){
            //writing the file
            gson.toJson(group,pw);
            return group;
        }catch (IOException e) {    //da cambiare
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Group> readByNameId(String groupNameId) {
        if (groupNameId.contains(JSON_TYPE)){
            groupNameId = groupNameId.replaceAll(JSON_TYPE,"");
        }
        try (Reader reader = new FileReader(JSON_DIR_PATH + groupNameId + JSON_TYPE)) {
            //read the group object, if null return optional empty
            return Optional.ofNullable(gson.fromJson(reader, Group.class));
        } catch (FileNotFoundException e){  //if the file(group) does not exist
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {    //da cambiare
            System.out.println("weeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Group update(Group group) {
        try (FileOutputStream output = new FileOutputStream(JSON_DIR_PATH + group.getGroupNameId() + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output)){
            //writing the file
            gson.toJson(group,pw);
            return group;
        }catch (IOException e) {    //da cambiare
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteByNameId(String groupNameId) {
        return new File(JSON_DIR_PATH + groupNameId + JSON_TYPE).delete();
    }

    @Override
    public Iterable<Group> findAllGroup() {
        File[] files = new File("groups").listFiles();
        Collection<Group> groupList = new ArrayList<>();
        if (files != null){
            //finding if file name match the current group name
            Arrays.stream(files).forEach(file -> {
                groupList.add(readByNameId(file.getName()).orElseThrow());
            });
        }
        return groupList;
    }


    static boolean groupNameFileExist(String groupNameId){
        //read the folder and then all the files in it
        File[] files = new File("groups").listFiles();
        if (files != null){
            //finding if file name match the current group name
            return Arrays.stream(files).anyMatch(f -> f.getName().equals(groupNameId + JSON_TYPE));
        }
        return false;
    }
}
