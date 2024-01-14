package com.github.rcored.project_auto_pass.model.utilities.gson;

import com.github.rcored.project_auto_pass.model.entities.platforms.Default;
import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PlatformTypeAdapter extends TypeAdapter<Platform> {
    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, Platform value) throws IOException {
        //value dovrebbe essere la Platform dell'account
        gson.toJson(value, Default.class, out); //lo scrivo sul file
    }

    @Override
    public Platform read(JsonReader in) throws IOException {
        int id = 0;

        in.beginObject();

        while (in.hasNext()){
            if (in.nextName().equals("id")){    //controllo se il prossimo campo è id (dovrebbe essere superfluo ma sempre meglio controllare)
                id  = in.nextInt();
            }else {
                in.nextString();    //serve per portare avanti "in" fino alla fine dell'oggetto json (se no impazzisce)
            }
        }

        in.endObject();

        return Platform.getPLATFORM_MAP().get(id);
    }
}
/*
La classe PlatformTypeAdapter estende la classe TypeAdapter di Gson. Questa è una classe astratta che definisce due metodi, write e read, che devono essere implementati. Questi metodi sono usati da Gson per serializzare e deserializzare oggetti.

Il metodo write(JsonWriter out, Platform value) viene chiamato quando Gson deve serializzare un oggetto Platform. Questo metodo scrive l’oggetto Platform nel JsonWriter fornito. Nel nostro caso, stiamo scrivendo il tipo di Platform (ad esempio, “Default”) e le sue proprietà come una stringa JSON.

Il metodo read(JsonReader in) viene chiamato quando Gson deve deserializzare un oggetto Platform. Questo metodo legge l’oggetto Platform dal JsonReader fornito. Nel nostro caso, stiamo leggendo il tipo di Platform e le sue proprietà da una stringa JSON, e poi stiamo creando un nuovo oggetto Platform di quel tipo.

Nel tuo caso specifico, stiamo gestendo solo oggetti Platform di tipo Default. Se avessi altri tipi di Platform, dovresti aggiungere la logica per gestire quei tipi nel tuo TypeAdapter.
*/