package com.snake.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.snake.Model.Save;

// Made by Marinus Juhl

/**
 * Static utility used for saving to and loading from the savefile system. Accessible methods
 * include: {@link #getSaveNames() getSaveNames}, {@link #isSavePresent() isSavePresent},
 * {@link #readSave() readSave}, {@link #writeSave() writeSave}
 */
public class SaveHandler
{
    private static Save[] saves = new Save[4];

    /**
     * Returns the savenames of the savefiles if present. If there is no name for a save the string
     * will be null. Index is offset 1 from the actual save indices: 1, 2, 3.
     *
     * @return 3 long String[]
     */
    public static String[] getSaveNames()
    {
        String[] saveNames = new String[3];
        try
        {
            readSavesToLocal();

            for (int i = 1; i < 4; i++)
            {
                if (saves[i] != null)
                {
                    saveNames[i - 1] = saves[i].getName();
                }
                else
                {
                    saveNames[i - 1] = null;
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

    /**
     *
     * @param index
     * @return true if there is data saved in the index, otherwise false.
     */
    public static boolean isSavePresent(int index)
    {
        boolean res = false;

        try
        {
            readSavesToLocal();

            if (saves[index] != null)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return res;
    }

    /**
     * Simple read function. If there is no Save object it will return null and print that the save
     * was empty.
     *
     * @param index
     * @return the Save object from the specified index.
     */
    public static Save readSave(int index)
    {
        Save save = null;

        try
        {
            readSavesToLocal();

            save = saves[index];
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

    /**
     * A simple write function. Prints the index written to.
     *
     * @param save the Save object being written to the file.
     * @param index
     */
    public static void writeSave(Save save, int index)
    {
        try
        {
            readSavesToLocal();

            Gson gson = new Gson();

            saves[index] = save;

            String path = "Save" + index + ".json";
            FileWriter writer = new FileWriter(path);
            gson.toJson(saves[index], writer);
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

    /**
     * Private function used to synchronise the saves in the local variable with the ones stored in
     * the files.
     *
     * @throws Exception
     */
    private static void readSavesToLocal() throws Exception
    {
        Gson gson = new Gson();

        for (int i = 0; i < 4; i++)
        {
            String path = "Save" + i + ".json";
            JsonReader reader = new JsonReader(new FileReader(path));

            saves[i] = gson.fromJson(reader, Save.class);
        }
    }

    /**
     * Function used at startup to check if all expected savefiles exist, and if they don't creates
     * them.
     *
     * @throws IOException
     */
    public static void createDummySaves() throws IOException
    {
        String path;
        for (int i = 0; i < 4; i++)
        {
            path = "Save" + i + ".json";
            File myObj = new File(path);
            if (!myObj.exists() && !myObj.isDirectory())
            {
                myObj.createNewFile();
                System.out.println("created file: " + path);
            }
        }
    }
}
