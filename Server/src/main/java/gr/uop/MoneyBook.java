package gr.uop;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class MoneyBook {
    String book="Book.txt";
    // clientinfo

    public void inputServiceInfo(ClientInfo x,LocalDateTime now){
        
        //var newX =x;

        try {
            PrintWriter bk = new PrintWriter(book);
            bk.println("Date & Time: "+now);
            bk.println("VehicleType: "+x.getVehicleType());
            bk.println("VehicleId: "+x.getVehicleType());
            bk.println("Servises: "+x.getSelectedServices());
            bk.println("Cost: "+x.getregNumber());
            bk.println("");
            bk.close();
        } 
        catch (FileNotFoundException e) {
            System.err.println("File doesn't exist.");
            e.printStackTrace();
        } 
        
    }
   /*  public void CloseBook(){
        book.close();
    }*/
}
