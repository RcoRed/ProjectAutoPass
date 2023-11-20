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

/** Implement all the CRUD operation for a Group
 * @author Marco Martucci
 * @version 0.1.0
 * */
@AllArgsConstructor
@Setter
@Getter
public class GroupCrudRepository implements AbstractGroupCrudRepository {
    /** Represents the service (gson library) that manages the creation and reading of a json file */
    private Gson gson;
    /** Create a file json of the Group, with the same groupNameFile name.
     * @param group It's the Group that will be written in the file with the same name of groupNameFile.
     * @return The same Group that you created
     * @exception DataException It's an IOException, and it's throw when there is a problem writing the file.
     * @since 0.1.0
     * */
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
    /** Read the file json of the Group, with the same groupNameFile name.
     * @param groupNameId It's the groupNameId (id) of the Group that you want to read
     * @return an Optional<Group> containing the Group written in the file or nothing if the file exist but is empty?
     * @exception DataException It's an IOException, and it's throw when there is a problem reading the file.
     * @exception EntityNotFoundException It's a FileNotFoundException, and it's throw when the file with the name groupNameId does not exist
     * @since 0.1.0
     * */
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
    /** Update the file json of the Group, with the same groupNameFile name.
     *  The method rewrite the file with the new Group inside.
     *  (Yes it's like the create method)
     * @param newGroup It's the Group that take the place of the old Group in the file, with the same file name and same groupNameId.
     * @return The new Group (newGroup).
     * @exception DataException It's an IOException, and it's throw when there is a problem writing the file.
     * @since 0.1.0
     * */
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
    /** Delete the file json with groupNameId
     * @param groupNameId It's the groupNameId (id) of the Group (or file) that you want to delete
     * @return a boolean <p>TRUE: if the file is deleted correctly</p> <p>FALSE: otherwise</p>
     * @since 0.1.0
     * */
    @Override
    public boolean deleteByNameId(String groupNameId){
        return new File(GROUP_DIR_PATH_ + groupNameId + JSON_TYPE).delete();
    }
    /** Search if the file with the name groupNameId exist or not
     * @param groupNameId It's the name of the file json that you want to know if it exists or not
     * @return a boolean <p>TRUE: if the file is exist</p> <p>FALSE: otherwise</p>
     * @since 0.1.0
     * */
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
