package com.github.rcored.project_auto_pass.model.data.implementations;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractCrudRepository;
import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.data.exceptions.EntityNotFoundException;
import com.github.rcored.project_auto_pass.model.entities.User;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

import static com.github.rcored.project_auto_pass.model.data.Constants.*;

/** Implement all the CRUD operation for the User (only one).
 * @author Marco Martucci
 * @version 0.1.0
 * */
@AllArgsConstructor
@Setter
@Getter
public class UserJsonCrudRepository implements AbstractCrudRepository<User,String> {

    private Gson gson;

    /** Create a file json of the User.
     * @param user It's the User that will be written in the user file.
     * @return The same User that you created
     * @exception DataException It's an IOException, and it's throw when there is a problem writing the file.
     * @since 0.1.0
     * */
    @SuppressWarnings("ResultOfMethodCallIgnored")  //the return of delete() is not useful
    @Override
    public User create(User user) throws DataException {
        File file = new File(USER_DIR_NAME);
        if(file.mkdir()) {
            String path = USER_DIR_PATH_ + USER_DIR_NAME + JSON_TYPE;
            try (FileOutputStream output = new FileOutputStream(path);
                 PrintWriter pw = new PrintWriter(output)) {
                //writing the file
                gson.toJson(user, pw);
                return user;
            } catch (IOException e) {
                file.delete();
                throw new DataException("Error creating the USER file: " + USER_DIR_NAME + " | " + user + " | " + e.getMessage(), e.getCause());
            }
        }
        throw new DataException("Error creating the USER directory: " + USER_DIR_NAME);
    }

    /** Read the file json of the User.
     * @param id At the moment it doesn't matter because there is only one User. Please use null
     * @return an Optional<User> containing the User written in the file or nothing if the file exist but is empty
     * @exception DataException It's an IOException, and it's throw when there is a problem reading the file.
     * @exception EntityNotFoundException It's a FileNotFoundException, and it's throw when the user file does not exist
     * @since 0.1.0
     * */
    @Override
    public Optional<User> readById(String id) throws EntityNotFoundException, DataException {
        String path = ABSOLUTE_GROUPS_DIR_PATH_ + USER_DIR_NAME + JSON_TYPE;
        try (Reader reader = new FileReader(path)) {
            //create a group with the json
            return Optional.of(gson.fromJson(reader, User.class));
        } catch (FileNotFoundException e) {
            throw new EntityNotFoundException("Unable to readById the USER file. " + e.getMessage(), e.getCause());
        } catch (IOException e) {
            throw new DataException("Error reading the USER file. " + e.getMessage(),e.getCause());
        }
    }

    /** Update the file json of the User.
     *  The method rewrite the file with the new Group inside.
     *  (Yes it's like the create method)
     * @param newUser It's the User that take the place of the old User in the file, with the same file name.
     * @return The new User (newUser).
     * @exception DataException It's an IOException, and it's throw when there is a problem writing the file.
     * @since 0.1.0
     * */
    @Override
    public User update(User newUser) throws DataException {
        if(existById(null)) {
            String path = USER_DIR_PATH_ + USER_DIR_NAME + JSON_TYPE;
            try (FileOutputStream output = new FileOutputStream(path);
                 PrintWriter pw = new PrintWriter(output)) {
                //writing the file
                gson.toJson(newUser, pw);
                return newUser;
            } catch (IOException e) {
                throw new DataException("Error creating the USER file: " + USER_DIR_NAME + " | " + newUser + " | " + e.getMessage(), e.getCause());
            }
        }
        throw new DataException("Error, the USER does not exists: " + newUser);
    }

    /** Delete the User directory and all the Groups inside.
     * @param USER Please use the constant USER_DIR_NAME from the class Constants.
     * @since 0.1.0
     * */
    @Override
    public void deleteById(String USER) {
        //take the user directory
        File file = new File(USER_DIR_PATH_ + USER);
        //take all the files inside the user directory
        File[] files = file.listFiles();
        if (files != null){
            //delete every file inside the directory
            Arrays.stream(files).forEach(f -> {
                if (f.isDirectory()){
                    deleteById(ABSOLUTE_GROUPS_DIR_NAME);
                }
            });
        }
        //delete the User directory
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

    /** Search if the User file exist or not.
     * @param USER Please use the constant USER_DIR_NAME from the class Constants.
     * @return a boolean <p>TRUE: if the file is exist</p> <p>FALSE: otherwise</p>
     * @since 0.1.0
     * */
    @Override
    public boolean existById(String USER) {
        //readById the folder and then all the files in it
        File[] files = new File(ABSOLUTE_GROUPS_DIR_PATH_ + USER + "/").listFiles();
        boolean exist = false;
        if (files != null){
            //finding if the user file exists
            exist = Arrays.stream(files).anyMatch(f -> f.getName().equals(USER + JSON_TYPE));
        }
        return exist;
    }
}
