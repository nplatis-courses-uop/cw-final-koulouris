package gr.uop;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface MoneyBook {
    final static String BOOK_NAME = "Book.data";

    /**
     * Appends given ClientInfo to the book
     * @param x the ClientInfo to append
     */
    public static void inputServiceInfo(ClientInfo x){
        ArrayList<ClientInfo> all = loadAllEntries();
        all.add(x);
        writeAll(all);
    }


    public static void update(ClientInfo clientInf, LocalDateTime departureTime){
        ArrayList<ClientInfo> all = loadAllEntries();
        int index = all.indexOf((ClientInfo)clientInf);
        ClientInfo c = all.get(index);
        c.setDepartureTime(departureTime);
        all.set(index, c);
        writeAll(all);
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
            fromFile = new ObjectInputStream(new FileInputStream(BOOK_NAME));
            opened = true;
            while(true){//will throw EOFException when it finishes reading from file
                ClientInfo ci = (ClientInfo)fromFile.readObject();
                ret.add(ci);
            }
        }catch(EOFException e){
            //finished reading from file
        }catch(FileNotFoundException l){//create new empty file
            File f = new File(BOOK_NAME);
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("Error creating file");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error reading from file");
            e.printStackTrace();
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

    /**
     * removes given entry from file, if it exists
     * @param ci the ClientInfo entry to remove
     */
    public static void removeEntry(ClientInfo ci) {
        ArrayList<ClientInfo> all = loadAllEntries();
        all.remove((ClientInfo)ci);
        writeAll(all);
    }
    /**
     * Replaces contents of file with given data
     * @param data the data to be written in the file, replacing anything previously written
     */
    private static void writeAll(ArrayList<ClientInfo> data){
        try (FileOutputStream fos = new FileOutputStream(BOOK_NAME, false)) {
            ObjectOutputStream toFile = new ObjectOutputStream(fos);
            for(ClientInfo Ci: data){
                toFile.writeObject((ClientInfo)Ci);
            }
            toFile.flush();//make sure all data is written in the file
            toFile.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
