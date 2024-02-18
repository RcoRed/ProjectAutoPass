package com.github.rcored.project_auto_pass.model.data.implementations;

import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Config;
import com.github.rcored.project_auto_pass.model.entities.Group;
import com.github.rcored.project_auto_pass.model.entities.User;
import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import com.github.rcored.project_auto_pass.model.utilities.gson.PlatformTypeAdapter;
import com.github.rcored.project_auto_pass.security.Session;
import com.github.rcored.project_auto_pass.security.encryption.abstractions.AbstractEncryption;
import com.github.rcored.project_auto_pass.security.encryption.implementations.EncryptionAES;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.*;
import java.util.HashMap;
import java.util.Map;

import static com.github.rcored.project_auto_pass.model.data.Constants.*;
import static com.github.rcored.project_auto_pass.model.data.DataTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class GroupJsonCrudRepositoryTest {
    Gson gson;
    AbstractEncryption encryption;
    GroupJsonCrudRepository repo;
    File fileGroupDir;
    Group group;
    Account account1;
    Account account2;
    Account account3;
    User user;
    Config config;
    Session session;
    Map<Integer,Account> accountMapSetUp;
    Map<String,Config> userConfig;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    @BeforeEach
    void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(Platform.class, new PlatformTypeAdapter()).create();
        Security.insertProviderAt(new BouncyCastleProvider(),1);
        encryption = new EncryptionAES();
        repo = new GroupJsonCrudRepository(gson,encryption);
        new File(USER_DIR_NAME).mkdir();
        fileGroupDir = new File(ABSOLUTE_GROUPS_DIR_NAME);
        fileGroupDir.mkdir();

        accountMapSetUp = new HashMap<>();
        userConfig = new HashMap<>();

        session = new Session(MessageDigest.getInstance(SHA3_HASHING).digest(USER_PASSWORD_TEST1.getBytes()));

        config = new Config(AES_CTR_PKCS5PADDING_ENCRYPTION,encryption.getCipher().getIV());
        userConfig.put(GROUP_NAME_TEST,config);
        user = new User(MessageDigest.getInstance(SHA3_HASHING).digest(USER_PASSWORD_TEST1.getBytes()),"",userConfig);

        account1 = new Account(ACCOUNT_NAME_TEST1,ACCOUNT_EMAIL_TEST1,ACCOUNT_PASSWORD_TEST1,Platform.getPLATFORM_MAP().get(1));
        account1.setId(1);
        accountMapSetUp.put(account1.getId(),account1);

        account2 = new Account(ACCOUNT_NAME_TEST2,ACCOUNT_EMAIL_TEST2,ACCOUNT_PASSWORD_TEST2,Platform.getPLATFORM_MAP().get(1));
        account2.setId(2);
        accountMapSetUp.put(account2.getId(),account2);

        account3 = new Account(ACCOUNT_NAME_TEST3,ACCOUNT_EMAIL_TEST3,ACCOUNT_PASSWORD_TEST3,Platform.getPLATFORM_MAP().get(1));
        account3.setId(3);
        accountMapSetUp.put(account3.getId(),account3);

        group = new Group(GROUP_NAME_TEST,accountMapSetUp,new byte[0]);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @AfterEach
    void tearDown() {
        new File(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + "/" + GROUP_NAME_TEST + JSON_TYPE).delete();
        new File(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST).delete();
        fileGroupDir.delete();
        new File(USER_DIR_PATH_ + USER_DIR_NAME).delete();
        new File(USER_DIR_NAME).delete();
    }

    @Test
    void create() {

        try {
            repo.create(group);
        } catch (DataException e) {
            fail(e.getMessage(), e.getCause());
        }

//        try (Reader reader = new FileReader(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE))
//        {
//            File[] filesAfter = fileGroupDir.listFiles();
//            assertNotNull(filesAfter);
//            assertTrue(filesBefore.length < filesAfter.length);
//
//            Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
//                    .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//            Optional<Group> optionalGroup = Optional.ofNullable(gson.fromJson(reader, Group.class));
//            assertTrue(optionalGroup.isPresent());
//            Group groupTest = optionalGroup.get();
//
//            assertEquals(GROUP_NAME_TEST, groupTest.getGroupNameId());
//            assertFalse(groupTest.getAccounts().isEmpty());
//            assertEquals(accountMapSetUp.size(),groupTest.getAccounts().size());
//            assertEquals(account1, groupTest.getAccounts().get(1));
//            assertEquals(account2, groupTest.getAccounts().get(2));
//            assertEquals(account3, groupTest.getAccounts().get(3));
//        } catch (IOException e) {
//            fail("Error reading the fileGroupDir " + e.getMessage(),e.getCause());
//        }
    }

//    @Test
//    void readByNameId() {
//        File[] filesBefore = fileGroupDir.listFiles();
//        assertNotNull(filesBefore);
//
//        try (FileOutputStream output = new FileOutputStream(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
//             PrintWriter pw = new PrintWriter(output))
//        {
//            gson.toJson(group,pw);
//        } catch (IOException e) {
//            fail("Error creating the fileGroupDir " + e.getMessage(),e.getCause());
//        }
//
//        File[] filesAfter = fileGroupDir.listFiles();
//        assertNotNull(filesAfter);
//        assertTrue(filesBefore.length < filesAfter.length);
//
//        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
//                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//        try{
//            Optional<Group> optionalGroup = repo.readById(GROUP_NAME_TEST);
//            assertTrue(optionalGroup.isPresent());
//            Group groupTest = optionalGroup.get();
//
//            assertEquals(group, groupTest);
//            assertFalse(groupTest.getAccounts().isEmpty());
//            assertEquals(accountMapSetUp.size(),groupTest.getAccounts().size());
//            assertEquals(account1, groupTest.getAccounts().get(1));
//            assertEquals(account2, groupTest.getAccounts().get(2));
//            assertEquals(account3, groupTest.getAccounts().get(3));
//        } catch (DataException | EntityNotFoundException e) {
//            fail(e.getMessage(),e.getCause());
//        }
//    }
//
//    @Test
//    void readByNameId_should_throw_EntityNotFoundException() {
//        File[] filesBefore = fileGroupDir.listFiles();
//        assertNotNull(filesBefore);
//
//        try (FileOutputStream output = new FileOutputStream(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
//             PrintWriter pw = new PrintWriter(output))
//        {
//            gson.toJson(group,pw);
//        } catch (IOException e) {
//            fail("Error creating the fileGroupDir " + e.getMessage(),e.getCause());
//        }
//
//        File[] filesAfter = fileGroupDir.listFiles();
//        assertNotNull(filesAfter);
//        assertTrue(filesBefore.length < filesAfter.length);
//
//        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
//                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//        try{
//            repo.readById(GROUP_NAME_TEST_ERROR);
//        } catch (DataException | EntityNotFoundException e) {
//            assertTrue(e instanceof EntityNotFoundException);
//        }
//    }
//
//    @Test
//    void update() {
//        File[] filesBefore = fileGroupDir.listFiles();
//        assertNotNull(filesBefore);
//
//        try (FileOutputStream output = new FileOutputStream(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
//             PrintWriter pw = new PrintWriter(output))
//        {
//            gson.toJson(group,pw);
//        } catch (IOException e) {
//            fail("Error creating the fileGroupDir " + e.getMessage(),e.getCause());
//        }
//
//        File[] filesAfter = fileGroupDir.listFiles();
//        assertNotNull(filesAfter);
//        assertTrue(filesBefore.length < filesAfter.length);
//
//        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
//                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//        Map<Integer,Account> accountMapTest = new HashMap<>();
//        Account account1Test = new Account(ACCOUNT_NAME_TEST1,ACCOUNT_EMAIL_TEST1,ACCOUNT_PASSWORD_TEST1,Platform.getPLATFORM_MAP().get(1));
//        account1Test.setId(1);
//        accountMapTest.put(account1Test.getId(), account1Test);
//
//        Account account2Test = new Account(ACCOUNT_NAME_TEST2,ACCOUNT_EMAIL_TEST2,ACCOUNT_PASSWORD_TEST2,Platform.getPLATFORM_MAP().get(1));
//        account2Test.setId(2);
//        accountMapTest.put(account2Test.getId(), account2Test);
//
//        Account account3Test = new Account(ACCOUNT_NAME_TEST3,ACCOUNT_EMAIL_TEST3,ACCOUNT_PASSWORD_TEST3,Platform.getPLATFORM_MAP().get(1));
//        account3Test.setId(3);
//        accountMapTest.put(account3Test.getId(), account3Test);
//
//        Group groupMod = new Group(group.getGroupNameId(), accountMapTest);
//        assertEquals(group,groupMod);
//        accountMapTest.remove(3);
//        groupMod.getAccounts().get(1).setName(ACCOUNT_NAME_TEST_ERROR);
//        groupMod.getAccounts().get(2).setEmail(ACCOUNT_EMAIL_TEST_ERROR);
//        assertNotEquals(group,groupMod);
//
//        try (Reader reader = new FileReader(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE))
//        {
//            Group groupTestReturn = repo.update(groupMod);
//
//            File[] filesAfterUpdate = fileGroupDir.listFiles();
//            assertNotNull(filesAfterUpdate);
//            assertEquals(filesAfter.length,filesAfterUpdate.length);
//            assertNotEquals(filesBefore.length,filesAfterUpdate.length);
//
//            Optional<Group> optionalGroup = Optional.ofNullable(gson.fromJson(reader, Group.class));
//            assertTrue(optionalGroup.isPresent());
//            Group groupTestRead = optionalGroup.get();
//
//            assertEquals(groupMod,groupTestReturn);
//            assertEquals(groupTestReturn,groupTestRead);
//            assertNotEquals(group,groupTestRead);
//
//            assertEquals(2,groupTestReturn.getAccounts().size());
//            assertEquals(groupMod.getAccounts().size(),groupTestReturn.getAccounts().size());
//            assertEquals(groupTestReturn.getAccounts().size(),groupTestRead.getAccounts().size());
//            assertNotEquals(group.getAccounts().size(),groupTestRead.getAccounts().size());
//
//            assertEquals(groupMod.getAccounts().get(1),groupTestReturn.getAccounts().get(1));
//            assertEquals(groupTestReturn.getAccounts().get(1),groupTestRead.getAccounts().get(1));
//            assertNotEquals(group.getAccounts().get(1),groupTestRead.getAccounts().get(1));
//            assertEquals(groupMod.getAccounts().get(2),groupTestReturn.getAccounts().get(2));
//            assertEquals(groupTestReturn.getAccounts().get(2),groupTestRead.getAccounts().get(2));
//            assertNotEquals(group.getAccounts().get(2),groupTestRead.getAccounts().get(2));
//
//        } catch (DataException e) {
//            fail(e.getMessage(),e.getCause());
//        } catch (IOException e) {
//            fail("Error reading the fileGroupDir " + e.getMessage(),e.getCause());
//        }
//    }
//
//    @Test
//    void deleteByNameId() {
//        File[] filesBefore = fileGroupDir.listFiles();
//        assertNotNull(filesBefore);
//
//        try (FileOutputStream output = new FileOutputStream(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
//             PrintWriter pw = new PrintWriter(output))
//        {
//            gson.toJson(group,pw);
//        } catch (IOException e) {
//            fail("Error creating the fileGroupDir " + e.getMessage(),e.getCause());
//        }
//
//        File[] filesAfter = fileGroupDir.listFiles();
//        assertNotNull(filesAfter);
//        assertTrue(filesBefore.length < filesAfter.length);
//
//        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
//                        .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//        assertTrue(repo.deleteById(group.getGroupNameId()));
//
//        filesAfter = fileGroupDir.listFiles();
//        assertNotNull(filesAfter);
//        assertEquals(filesBefore.length,filesAfter.length);
//
//        Arrays.stream(filesAfter).forEach(f -> assertNotEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//    }
//
//    @Test
//    void deleteByNameId_should_not_delete() {
//        File[] filesBefore = fileGroupDir.listFiles();
//        assertNotNull(filesBefore);
//
//        try (FileOutputStream output = new FileOutputStream(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
//             PrintWriter pw = new PrintWriter(output))
//        {
//            gson.toJson(group,pw);
//        } catch (IOException e) {
//            fail("Error creating the fileGroupDir " + e.getMessage(),e.getCause());
//        }
//
//        File[] filesAfter = fileGroupDir.listFiles();
//        assertNotNull(filesAfter);
//        assertTrue(filesBefore.length < filesAfter.length);
//
//        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
//                        .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//        assertFalse(repo.deleteById(GROUP_NAME_TEST_ERROR));
//
//        filesAfter = fileGroupDir.listFiles();
//        assertNotNull(filesAfter);
//        assertNotEquals(filesBefore.length,filesAfter.length);
//
//        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
//                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//    }
//
//    @Test
//    void groupNameFileExist() {
//        File[] filesBefore = fileGroupDir.listFiles();
//        assertNotNull(filesBefore);
//
//        try (FileOutputStream output = new FileOutputStream(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
//             PrintWriter pw = new PrintWriter(output))
//        {
//            gson.toJson(group,pw);
//        } catch (IOException e) {
//            fail("Error creating the fileGroupDir " + e.getMessage(),e.getCause());
//        }
//
//        File[] filesAfter = fileGroupDir.listFiles();
//        assertNotNull(filesAfter);
//        assertTrue(filesBefore.length < filesAfter.length);
//
//        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
//                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//        assertTrue(repo.existById(GROUP_NAME_TEST));
//    }
//
//    @Test
//    void groupNameFileExist_should_not_exist() {
//        File[] filesBefore = fileGroupDir.listFiles();
//        assertNotNull(filesBefore);
//
//        try (FileOutputStream output = new FileOutputStream(ABSOLUTE_GROUPS_DIR_PATH_ + GROUP_NAME_TEST + JSON_TYPE);
//             PrintWriter pw = new PrintWriter(output))
//        {
//            gson.toJson(group,pw);
//        } catch (IOException e) {
//            fail("Error creating the fileGroupDir " + e.getMessage(),e.getCause());
//        }
//
//        File[] filesAfter = fileGroupDir.listFiles();
//        assertNotNull(filesAfter);
//        assertTrue(filesBefore.length < filesAfter.length);
//
//        Arrays.stream(filesAfter).filter(f -> f.getName().equals(GROUP_NAME_TEST + JSON_TYPE))
//                .forEach(f -> assertEquals(GROUP_NAME_TEST + JSON_TYPE,f.getName()));
//
//        assertFalse(repo.existById(GROUP_NAME_TEST_ERROR));
//    }
}