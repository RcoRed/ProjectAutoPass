package com.github.rcored.project_auto_pass.model.data;

public class Constants {
    public static final String JSON_TYPE = ".json";
    public static final String USER_DIR_NAME = "user";
    public static final String USER_DIR_PATH_ = USER_DIR_NAME + "/";            //with / at the end
    public static final String GROUPS_DIR_NAME = "groups";      //without / at the end
    public static final String GROUPS_DIR_PATH_ = GROUPS_DIR_NAME + "/";    //with / at the end
    public static final String ABSOLUTE_GROUPS_DIR_PATH_ = USER_DIR_PATH_ + GROUPS_DIR_PATH_;    //with / at the end
    public static final String ABSOLUTE_GROUPS_DIR_NAME = USER_DIR_PATH_+ GROUPS_DIR_NAME;      //without / at the end
    public static final String CONFIG_FILE_NAME = "config";


    public static final String AES_CTR_PKCS5PADDING_ENCRYPTION = "AES/CTR/PKCS5Padding";
    public static final String SHA3_HASHING = "SHA3-256";


}
