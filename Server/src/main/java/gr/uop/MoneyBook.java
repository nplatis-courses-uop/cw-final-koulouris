package gr.uop;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MoneyBook {
    final static String BOOK_NAME = "Book.data";

    /**
     * Appends given ClientInfo to the book
     * @param x the ClientInfo to append
     */
    public static void inputServiceInfo(ClientInfo x){
        try (FileOutputStream fos = new FileOutputStream(BOOK_NAME, true)) {
            ObjectOutputStream toFile = new ObjectOutputStream(fos);
            toFile.writeObject((ClientInfo)x);
            toFile.flush();//make sure all data is written in the file
            toFile.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
    }

    /**
     * load every entry saved in the file BOOK_NAME
     * @return an ArrayList of all entries in the file
     */
    public static ArrayList<ClientInfo> loadAllEntries(){//
        ArrayList<ClientInfo> ret = new ArrayList<>();
        ObjectInputStream fromFile = null;
        boolean opened = false;

        try{ 
            File f = new File(BOOK_NAME);
            f.createNewFile();
            fromFile = new ObjectInputStream(new FileInputStream(f));
            opened = true;
            while(true){
                ClientInfo ci = (ClientInfo)fromFile.readObject();
                ret.add(ci);
            }
        } catch (IOException | ClassNotFoundException e) {
            //file is empty or does not exist
        } 
        if(opened){
            try {
                fromFile.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ret;
    }
}
