package com.github.rcored;

import com.github.rcored.project_auto_pass.model.utilities.gson.PlatformTypeAdapter;
import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
//import com.github.rcored.project_auto_pass.view.InLineView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Version;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.Security;
import java.util.Scanner;

import static com.github.rcored.project_auto_pass.model.data.Constants.ABSOLUTE_GROUPS_DIR_PATH_;
import static com.github.rcored.project_auto_pass.model.data.Constants.JSON_TYPE;

public class Main {
    //0.1.0
    private static int memory;
    private static int iteration;
    private static int parallelism;

    @SneakyThrows
    public static void main(String[] args) {

//        Security.addProvider(new BouncyCastleProvider());
        Security.insertProviderAt(new BouncyCastleProvider(),1);

        //Argon2 version is 19 (should be)
        System.out.println(Argon2Version.DEFAULT_VERSION.getVersion());

        //create an Argon2 object da eliminare
        //Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id,16,32);
        //insertPassword(argon2);

        //loads all the Platforms
        Platform.getPLATFORM_MAP();

        //create a Gson object
//        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Platform.class, new PlatformTypeAdapter()).create();
        Gson gson = new GsonBuilder().registerTypeAdapter(Platform.class, new PlatformTypeAdapter()).create();
        //create the view object
        //AbstractView view = new InLineView(new CrudService(new GroupJsonCrudRepository(gson)));

        //start the application
        //view.start();
    }
}