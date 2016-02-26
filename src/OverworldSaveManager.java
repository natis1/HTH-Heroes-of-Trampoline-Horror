import sun.security.krb5.internal.crypto.Des;

import java.io.*;
import java.util.Vector;

public class OverworldSaveManager {

    protected Vector<Long> loadFromSaveFile(String Destination){
        //Expand as needed
        Vector<Long> loadedData = new Vector<Long>();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(Destination);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            if (bufferedReader.readLine() != null) {
                loadedData.add(Long.valueOf(bufferedReader.readLine()));
                loadedData.add(Long.valueOf(bufferedReader.readLine()));
                loadedData.add(Long.valueOf(bufferedReader.readLine()));
                if (loadedData.elementAt(2) == 0){
                    loadedData.set(2, System.nanoTime());
                }
                bufferedReader.close();
            }




        } catch (FileNotFoundException e) {

            //This is fine
            e.printStackTrace();
        } catch (IOException e) {
            //Please restart or something
            e.printStackTrace();
        }

        saveToFile(Destination, loadedData.get(0), loadedData.get(1), loadedData.get(2));



        return loadedData;
    }




    protected void saveToFile(String Destination, long x, long y, long seed){


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
