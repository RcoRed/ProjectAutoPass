package com.github.rcored.project_auto_pass.security;

public class SecurityTestCostants {

    public static final String PLAIN_TEXT = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                et dolore magna aliqua. Ut enim ad minim veniam, [1234],[21] Group.equals(); quis nostrud exercitation ullamco laboris nisi ut
                aliquip ex ea commodo consequat. (int a = b / 0;) Duis aute     irure dolor in reprehenderit in voluptate velit esse
                cillum dolore eu fugiat nulla {+èòàùç°#@§^£$%&""|} pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                culpa qui officia deserunt mollit anim id est laborum.
            """;
    public static final String PLAIN_KEY = "La_Mia_Pa22word!";
    public static final String PLAIN_KEY2 = "Non_é_la_Pa22word!";
    public static final String SHA3_256_HASH = "SHA3-256";
    public static final int ARGON2_MIN_SALT = 16;
    public static final int ARGON2_MIN_HASH = 32;
    public static final int ARGON2_ITERATION = 1000;
    public static final int ARGON2_MEMORY = 2000; //MB
    public static final int ARGON2_PARALLELISM = 2;
}
