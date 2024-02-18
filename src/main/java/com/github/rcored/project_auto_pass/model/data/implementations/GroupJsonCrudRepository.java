package com.github.rcored.project_auto_pass.model.data.implementations;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractCrudRepository;
import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.data.exceptions.EntityNotFoundException;
import com.github.rcored.project_auto_pass.model.entities.Group;
import com.github.rcored.project_auto_pass.model.entities.User;
import com.github.rcored.project_auto_pass.security.Session;
import com.github.rcored.project_auto_pass.security.encryption.abstractions.AbstractEncryption;
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
public class GroupJsonCrudRepository implements AbstractCrudRepository<Group,String> {
    /** Represents the service (gson library) that manages the creation and reading of a json file */
    private Gson gson;
    /** Represents the encryption that will be used to encrypt the Group*/
    private AbstractEncryption encryption;

    /** Create a file json of the Group, with the same groupNameFile name.
     * @param group It's the Group that will be written in the file with the same name of groupNameFile.
     * @return The same Group that you created
     * @exception DataException It's an IOException, and it's throw when there is a problem writing the file.
     * @since 0.1.0
     * */
    @Override
    public Group create(Group group) throws DataException {
        File file = new File(ABSOLUTE_GROUPS_DIR_PATH_ + group.getGroupNameId());
        if(file.mkdir()) {
            String path = ABSOLUTE_GROUPS_DIR_PATH_ + group.getGroupNameId() + "/" + group.getGroupNameId() + JSON_TYPE;
            try (FileOutputStream output = new FileOutputStream(path);
                 PrintWriter pw = new PrintWriter(output)) {
                //encryption of the json version of the group
                byte[] byteJsonGroup = encryption.encrypt(gson.toJson(group), Session.getHPassword());
                //writing the file
                gson.toJson(byteJsonGroup, pw);
                return group;
            } catch (IOException e) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
                throw new DataException("Error creating the GROUP file: " + group + " | " + e.getMessage(), e.getCause());
            }
        }
        throw new DataException("Error creating the GROUP directory: " + group);
    }

    /** Read the file json of the Group, with the same groupNameFile name.
     * @param groupNameId It's the groupNameId (id) of the Group that you want to read
     * @return an Optional<Group> containing the Group written in the file or nothing if the file exist but is empty?
     * @exception DataException It's an IOException, and it's throw when there is a problem reading the file.
     * @exception EntityNotFoundException It's a FileNotFoundException, and it's throw when the file with the name groupNameId does not exist
     * @since 0.1.0
     * */
    @Override
    public Optional<Group> readById(String groupNameId) throws EntityNotFoundException, DataException {
        String path = ABSOLUTE_GROUPS_DIR_PATH_ + groupNameId + "/" + groupNameId + JSON_TYPE;
        try (Reader reader = new FileReader(path)) {
            //read the bytes representing the Group
            byte[] bytesGroup = gson.fromJson(reader, byte[].class);
            //if the reading result gives an empty byte[]
            if (bytesGroup.length < 1){
                return Optional.empty();
            }
            //decrypt the bytes to obtain the json of the Group
            String jsonGroup = encryption.decrypt(bytesGroup,Session.getHPassword(),User.getUser().getConfigMap().get(groupNameId).getIV());
            //create a group with the json
            return Optional.of(gson.fromJson(jsonGroup, Group.class));
        } catch (FileNotFoundException e) {
            throw new EntityNotFoundException("Unable to readById the GROUP file: " + groupNameId + e.getMessage(), e.getCause());
        } catch (IOException e) {
            throw new DataException("Error reading the GROUP file: " + groupNameId + e.getMessage(),e.getCause());
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
    public Group update(Group newGroup) throws DataException {
        if(existById(newGroup.getGroupNameId())) {
            String path = ABSOLUTE_GROUPS_DIR_PATH_ + newGroup.getGroupNameId() + "/" + newGroup.getGroupNameId() + JSON_TYPE;
            try (FileOutputStream output = new FileOutputStream(path);
                 PrintWriter pw = new PrintWriter(output)) {
                //encryption of the json version of the group
                byte[] byteJsonGroup = encryption.encrypt(gson.toJson(newGroup), Session.getHPassword());
                //writing the file
                gson.toJson(byteJsonGroup, pw);
                return newGroup;
            } catch (IOException e) {
                throw new DataException("Error creating the GROUP file: " + newGroup + " | " + e.getMessage(), e.getCause());
            }
        }
        throw new DataException("Error, the GROUP does not exists: " + newGroup);
    }

    /** Delete the groupNameId directory and all the file inside it.
     * @param groupNameId It's the groupNameId (id) of the Group (or file) that you want to delete
     * @since 0.1.0
     * */
    @Override
    public void deleteById(String groupNameId) {
        //take the directory groupNameId
        File file = new File(ABSOLUTE_GROUPS_DIR_PATH_ + groupNameId);
        //take all the files inside the directory groupNameId
        File[] files = file.listFiles();
        if (files != null){
            //delete every file inside the directory
            Arrays.stream(files).forEach(File::delete);
        }
        //delete the directory groupNameId
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

    /** Search if the file with the name groupNameId exist or not
     * @param groupNameId It's the name of the file json that you want to know if it exists or not
     * @return a boolean <p>TRUE: if the file is exist</p> <p>FALSE: otherwise</p>
     * @since 0.1.0
     * */
    @Override
    public boolean existById(String groupNameId) {
        //readById the folder and then all the files in it
        File[] files = new File(ABSOLUTE_GROUPS_DIR_PATH_ + groupNameId + "/").listFiles();
        boolean exist = false;
        if (files != null){
            //finding if the groupNameId file exists
            exist = Arrays.stream(files).anyMatch(f -> f.getName().equals(groupNameId + JSON_TYPE));
            //finding if the config file exists
            exist = exist && Arrays.stream(files).anyMatch(f -> f.getName().equals(CONFIG_FILE_NAME + JSON_TYPE));
        }
        return exist;
    }

}
