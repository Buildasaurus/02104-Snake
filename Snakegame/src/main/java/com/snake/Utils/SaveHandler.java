package com.snake.Utils;

import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.snake.Model.Save;

public class SaveHandler {
    private static Save[] saves = new Save[3];

    public static String[] getSaveNames() {
        String[] saveNames = new String[3];
        try {
            readSavesToLocal();
            
            for (int i = 0; i < 3; i++) {
                if (saves[i] != null) {
                    saveNames[i] = saves[i].getName();
                } else {
                    saveNames[i] = null;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return saveNames;
    }

    public static Save readSave(String saveName) {
        Save save = null;

        try {
            readSavesToLocal();

            for (int i = 0; i < 3; i++) {
                if (saves[i].getName().equals(saveName)); {
                    save = saves[i];
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if (save == null) {
            System.out.println("Couldn't find save");
        }

        return save;
    }

    public static void writeSave(Save save, int index) {
        try {
            readSavesToLocal();

            saves[index - 1] = save;

            Gson gson = new Gson();
            String path = "Save" + index + ".txt";
            FileWriter writer = new FileWriter(path);
            gson.toJson(saves, writer);
            writer.flush();
            writer.close();
            System.out.println("Wrote to: " + index);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void readSavesToLocal() throws Exception {
        Gson gson = new Gson();

        for (int i = 1; i <= 3; i++) {
            String path = "Save" + i + ".txt";
            JsonReader reader = new JsonReader(new FileReader(path));

            Save[] tempArray = new Save[1];
            tempArray = gson.fromJson(reader, Save[].class);

            saves[i - 1] = tempArray[0];
        }
    }
}
