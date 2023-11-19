package com.github.rcored;

import com.github.rcored.project_auto_pass.model.services.implementations.CrudService;
import com.github.rcored.project_auto_pass.model.utilities.gson.PlatformTypeAdapter;
import com.github.rcored.project_auto_pass.model.data.implementations.GroupCrudRepository;
import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import com.github.rcored.project_auto_pass.view.AbstractView;
import com.github.rcored.project_auto_pass.view.InLineView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        Platform.getPLATFORM_MAP(); //carica le Platform

        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Platform.class, new PlatformTypeAdapter()).create();

        AbstractView view = new InLineView(new CrudService(new GroupCrudRepository(gson)));

        view.start();
    }
}