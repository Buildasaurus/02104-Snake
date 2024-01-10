package com.snake.Utils;

import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.snake.Model.Save;

public class SaveHandler
{
    private static Save[] saves = new Save[3];

    public static String[] getSaveNames()
    {
        String[] saveNames = new String[3];
        try
        {
            readSavesToLocal();

            for (int i = 0; i < 3; i++)
            {
                if (saves[i] != null)
                {
                    saveNames[i] = saves[i].getName();
                }
                else
                {
                    saveNames[i] = null;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occured in SaveHandler");
            System.out.println(e);
        }
        return saveNames;
    }

    public static boolean isSavePresent(int index) {
        boolean res = false;

        try {
            readSavesToLocal();

            if (saves[index - 1] != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }

    public static Save readSave(int index)
    {
        Save save = null;

        try
        {
            readSavesToLocal();

            save = saves[index - 1];
        }
        catch (Exception e)
        {
            System.out.println("An error occured in SaveHandler");

            System.out.println(e);
        }
        if (save == null)
        {
            System.out.println("Save is empty");
        }

        return save;
    }

    public static void writeSave(Save save, int index)
    {
        try
        {
            readSavesToLocal();

            saves[index - 1] = save;

            Gson gson = new Gson();

            String path = "Save" + index + ".json";
            FileWriter writer = new FileWriter(path);
            gson.toJson(saves[index - 1], writer);
            writer.flush();
            writer.close();

            System.out.println("Wrote to: " + index);
        }
        catch (Exception e)
        {
            System.out.println("An error occured in SaveHandler");

            System.out.println(e);
        }
    }

    private static void readSavesToLocal() throws Exception
    {
        Gson gson = new Gson();

        for (int i = 1; i <= 3; i++)
        {
            String path = "Save" + i + ".json";
            JsonReader reader = new JsonReader(new FileReader(path));

            saves[i - 1] = gson.fromJson(reader, Save.class);
        }
    }
}
