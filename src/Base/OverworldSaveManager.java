package Base;

import java.io.*;
import java.util.Vector;



public class OverworldSaveManager {

    public Vector<Object> loadFromSaveFile(String Destination){
        //Expand as needed
        Vector<Object> loadedData = new Vector<Object>();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(Destination);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            if (bufferedReader.readLine() != null) {
                loadedData.add(Integer.valueOf(bufferedReader.readLine()));
                loadedData.add(Integer.valueOf(bufferedReader.readLine()));
                loadedData.add(Long.valueOf(bufferedReader.readLine()));
                bufferedReader.close();
            }




        } catch (FileNotFoundException e) {

            //This is fine
            e.printStackTrace();
        } catch (IOException e) {
            //Please restart or something
            e.printStackTrace();
        }

        saveToFile(Destination, (int) loadedData.get(0), (int) loadedData.get(1), (long) loadedData.get(2));



        return loadedData;
    }




    public void saveToFile(String Destination, int x, int y, long seed){


        PrintWriter fileWriter;
        try {
            fileWriter = new PrintWriter(Destination, "UTF-8");

            fileWriter.println("Save File. Do not edit you cheater");
            fileWriter.println(x);//X location
            fileWriter.println(y);//Y location
            fileWriter.println(seed);//Seed
            fileWriter.println("0");//Resolution (0 for fullscreen) This is the height (IE 1080p 720p etc)
            fileWriter.println("0");//Framerate
            fileWriter.close();



        } catch (FileNotFoundException e) {
            System.out.println(
                    "No idea why I can't make file Something is seriously wrong with your system");

        } catch (UnsupportedEncodingException e) {
            System.out.println(
                    "You do not have UTF-8? I am seriously amazed");
        }




    }




}
