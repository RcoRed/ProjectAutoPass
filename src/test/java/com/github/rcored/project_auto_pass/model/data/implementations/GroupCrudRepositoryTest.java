package com.github.rcored.project_auto_pass.model.data.implementations;

import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.data.exceptions.EntityNotFoundException;
import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;
import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import com.github.rcored.project_auto_pass.model.utilities.gson.PlatformTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.rcored.project_auto_pass.model.data.Constants.GROUP_DIR_PATH_;
import static com.github.rcored.project_auto_pass.model.data.Constants.JSON_TYPE;
import static com.github.rcored.project_auto_pass.model.data.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class GroupCrudRepositoryTest {
    Gson gson;
    GroupCrudRepository repo;
    File file;
    Group group;
    Account account1;
    Account account2;
    Account account3;
    Map<Integer,Account> accountMapSetUp;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Platform.class, new PlatformTypeAdapter()).create();
        repo = new GroupCrudRepository(gson);
        file = new File(GROUP_DIR_PATH);
        accountMapSetUp = new HashMap<>();

        Security.insertProviderAt(new BouncyCastleProvider(),1);


        account1 = new Account(ACCOUNT_NAME_TEST1,ACCOUNT_EMAIL_TEST1,ACCOUNT_PASSWORD_TEST1,Platform.getPLATFORM_MAP().get(1));
        account1.setId(1);
        accountMapSetUp.put(account1.getId(),account1);

        account2 = new Account(ACCOUNT_NAME_TEST2,ACCOUNT_EMAIL_TEST2,ACCOUNT_PASSWORD_TEST2,Platform.getPLATFORM_MAP().get(1));
        account2.setId(2);
        accountMapSetUp.put(account2.getId(),account2);

        account3 = new Account(ACCOUNT_NAME_TEST3,ACCOUNT_EMAIL_TEST3,ACCOUNT_PASSWORD_TEST3,Platform.getPLATFORM_MAP().get(1));
        account3.setId(3);
        accountMapSetUp.put(account3.getId(),account3);

        group = new Group(GROUP_NAME_TEST,accountMapSetUp);
    }

    @AfterEach
    void tearDown() {
        //noinspection ResultOfMethodCallIgnored
        new File(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE).delete();
    }

    @Test
    void create() {


        File[] filesBefore = file.listFiles();
        //la cartella (groups) deve già esistere
        assertNotNull(filesBefore);
        try {
            repo.create(group);
        } catch (DataException e) {
            fail(e.getMessage(), e.getCause());
        }

        try (Reader reader = new FileReader(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE))
        {
            File[] filesAfter = file.listFiles();
            assertNotNull(filesAfter);
            assertTrue(filesBefore.length < filesAfter.length);

            Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
                    .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

            Optional<Group> optionalGroup = Optional.ofNullable(gson.fromJson(reader, Group.class));
            assertTrue(optionalGroup.isPresent());
            Group groupTest = optionalGroup.get();

            assertEquals(GROUP_NAME_TEST, groupTest.getGroupNameId());
            assertFalse(groupTest.getAccounts().isEmpty());
            assertEquals(accountMapSetUp.size(),groupTest.getAccounts().size());
            assertEquals(account1, groupTest.getAccounts().get(1));
            assertEquals(account2, groupTest.getAccounts().get(2));
            assertEquals(account3, groupTest.getAccounts().get(3));
        } catch (IOException e) {
            fail("Error reading the file " + e.getMessage(),e.getCause());
        }
    }

    @Test
    void readByNameId() {
        File[] filesBefore = file.listFiles();
        assertNotNull(filesBefore);

        try (FileOutputStream output = new FileOutputStream(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
                PrintWriter pw = new PrintWriter(output))
        {
            gson.toJson(group,pw);
        } catch (IOException e) {
            fail("Error creating the file " + e.getMessage(),e.getCause());
        }

        File[] filesAfter = file.listFiles();
        assertNotNull(filesAfter);
        assertTrue(filesBefore.length < filesAfter.length);

        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

        try{
            Optional<Group> optionalGroup = repo.readByNameId(GROUP_NAME_TEST);
            assertTrue(optionalGroup.isPresent());
            Group groupTest = optionalGroup.get();

            assertEquals(group, groupTest);
            assertFalse(groupTest.getAccounts().isEmpty());
            assertEquals(accountMapSetUp.size(),groupTest.getAccounts().size());
            assertEquals(account1, groupTest.getAccounts().get(1));
            assertEquals(account2, groupTest.getAccounts().get(2));
            assertEquals(account3, groupTest.getAccounts().get(3));
        } catch (DataException | EntityNotFoundException e) {
            fail(e.getMessage(),e.getCause());
        }
    }

    @Test
    void readByNameId_should_throw_EntityNotFoundException() {
        File[] filesBefore = file.listFiles();
        assertNotNull(filesBefore);

        try (FileOutputStream output = new FileOutputStream(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output))
        {
            gson.toJson(group,pw);
        } catch (IOException e) {
            fail("Error creating the file " + e.getMessage(),e.getCause());
        }

        File[] filesAfter = file.listFiles();
        assertNotNull(filesAfter);
        assertTrue(filesBefore.length < filesAfter.length);

        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

        try{
            repo.readByNameId(GROUP_NAME_TEST_ERROR);
        } catch (DataException | EntityNotFoundException e) {
            assertTrue(e instanceof EntityNotFoundException);
        }
    }

    @Test
    void update() {
        File[] filesBefore = file.listFiles();
        assertNotNull(filesBefore);

        try (FileOutputStream output = new FileOutputStream(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output))
        {
            gson.toJson(group,pw);
        } catch (IOException e) {
            fail("Error creating the file " + e.getMessage(),e.getCause());
        }

        File[] filesAfter = file.listFiles();
        assertNotNull(filesAfter);
        assertTrue(filesBefore.length < filesAfter.length);

        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

        Map<Integer,Account> accountMapTest = new HashMap<>();
        Account account1Test = new Account(ACCOUNT_NAME_TEST1,ACCOUNT_EMAIL_TEST1,ACCOUNT_PASSWORD_TEST1,Platform.getPLATFORM_MAP().get(1));
        account1Test.setId(1);
        accountMapTest.put(account1Test.getId(), account1Test);

        Account account2Test = new Account(ACCOUNT_NAME_TEST2,ACCOUNT_EMAIL_TEST2,ACCOUNT_PASSWORD_TEST2,Platform.getPLATFORM_MAP().get(1));
        account2Test.setId(2);
        accountMapTest.put(account2Test.getId(), account2Test);

        Account account3Test = new Account(ACCOUNT_NAME_TEST3,ACCOUNT_EMAIL_TEST3,ACCOUNT_PASSWORD_TEST3,Platform.getPLATFORM_MAP().get(1));
        account3Test.setId(3);
        accountMapTest.put(account3Test.getId(), account3Test);

        Group groupMod = new Group(group.getGroupNameId(), accountMapTest);
        assertEquals(group,groupMod);
        accountMapTest.remove(3);
        groupMod.getAccounts().get(1).setName(ACCOUNT_NAME_TEST_ERROR);
        groupMod.getAccounts().get(2).setEmail(ACCOUNT_EMAIL_TEST_ERROR);
        assertNotEquals(group,groupMod);

        try (Reader reader = new FileReader(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE))
        {
            Group groupTestReturn = repo.update(groupMod);

            File[] filesAfterUpdate = file.listFiles();
            assertNotNull(filesAfterUpdate);
            assertEquals(filesAfter.length,filesAfterUpdate.length);
            assertNotEquals(filesBefore.length,filesAfterUpdate.length);

            Optional<Group> optionalGroup = Optional.ofNullable(gson.fromJson(reader, Group.class));
            assertTrue(optionalGroup.isPresent());
            Group groupTestRead = optionalGroup.get();

            assertEquals(groupMod,groupTestReturn);
            assertEquals(groupTestReturn,groupTestRead);
            assertNotEquals(group,groupTestRead);

            assertEquals(2,groupTestReturn.getAccounts().size());
            assertEquals(groupMod.getAccounts().size(),groupTestReturn.getAccounts().size());
            assertEquals(groupTestReturn.getAccounts().size(),groupTestRead.getAccounts().size());
            assertNotEquals(group.getAccounts().size(),groupTestRead.getAccounts().size());

            assertEquals(groupMod.getAccounts().get(1),groupTestReturn.getAccounts().get(1));
            assertEquals(groupTestReturn.getAccounts().get(1),groupTestRead.getAccounts().get(1));
            assertNotEquals(group.getAccounts().get(1),groupTestRead.getAccounts().get(1));
            assertEquals(groupMod.getAccounts().get(2),groupTestReturn.getAccounts().get(2));
            assertEquals(groupTestReturn.getAccounts().get(2),groupTestRead.getAccounts().get(2));
            assertNotEquals(group.getAccounts().get(2),groupTestRead.getAccounts().get(2));

        } catch (DataException e) {
            fail(e.getMessage(),e.getCause());
        } catch (IOException e) {
            fail("Error reading the file " + e.getMessage(),e.getCause());
        }
    }

    @Test
    void deleteByNameId() {
        File[] filesBefore = file.listFiles();
        assertNotNull(filesBefore);

        try (FileOutputStream output = new FileOutputStream(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output))
        {
            gson.toJson(group,pw);
        } catch (IOException e) {
            fail("Error creating the file " + e.getMessage(),e.getCause());
        }

        File[] filesAfter = file.listFiles();
        assertNotNull(filesAfter);
        assertTrue(filesBefore.length < filesAfter.length);

        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
                        .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

        assertTrue(repo.deleteByNameId(group.getGroupNameId()));

        filesAfter = file.listFiles();
        assertNotNull(filesAfter);
        assertEquals(filesBefore.length,filesAfter.length);

        Arrays.stream(filesAfter).forEach(f -> assertNotEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

    }

    @Test
    void deleteByNameId_should_not_delete() {
        File[] filesBefore = file.listFiles();
        assertNotNull(filesBefore);

        try (FileOutputStream output = new FileOutputStream(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output))
        {
            gson.toJson(group,pw);
        } catch (IOException e) {
            fail("Error creating the file " + e.getMessage(),e.getCause());
        }

        File[] filesAfter = file.listFiles();
        assertNotNull(filesAfter);
        assertTrue(filesBefore.length < filesAfter.length);

        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
                        .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

        assertFalse(repo.deleteByNameId(GROUP_NAME_TEST_ERROR));

        filesAfter = file.listFiles();
        assertNotNull(filesAfter);
        assertNotEquals(filesBefore.length,filesAfter.length);

        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

    }

    @Test
    void groupNameFileExist() {
        File[] filesBefore = file.listFiles();
        assertNotNull(filesBefore);

        try (FileOutputStream output = new FileOutputStream(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output))
        {
            gson.toJson(group,pw);
        } catch (IOException e) {
            fail("Error creating the file " + e.getMessage(),e.getCause());
        }

        File[] filesAfter = file.listFiles();
        assertNotNull(filesAfter);
        assertTrue(filesBefore.length < filesAfter.length);

        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

        assertTrue(repo.groupNameFileExist(GROUP_NAME_TEST));
    }

    @Test
    void groupNameFileExist_should_not_exist() {
        File[] filesBefore = file.listFiles();
        assertNotNull(filesBefore);

        try (FileOutputStream output = new FileOutputStream(GROUP_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
             PrintWriter pw = new PrintWriter(output))
        {
            gson.toJson(group,pw);
        } catch (IOException e) {
            fail("Error creating the file " + e.getMessage(),e.getCause());
        }

        File[] filesAfter = file.listFiles();
        assertNotNull(filesAfter);
        assertTrue(filesBefore.length < filesAfter.length);

        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));

        assertFalse(repo.groupNameFileExist(GROUP_NAME_TEST_ERROR));
    }
}