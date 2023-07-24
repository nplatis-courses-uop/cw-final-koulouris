package gr.uop;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ClientInfo implements Serializable{
    private int totalCost = 0;
    private Map<Service, Integer> serviceAndCost;
    private String vehicleType;
    private String regNumber;
    private String arrivalTime, departureTime;
    public final static String DATE_FORMAT_PATTERN = "HH:mm, dd-MM-yyyy";

    /**
     * Constructor for ClientInfo
     * @param vehicleType the type of the vehicle
     * @param regNumber the registration number of the vehicle
     * @param selectedServices the services that the client has selected
     */
    public ClientInfo(String vehicleType, String regNumber, ArrayList<Service> selectedServices){
        this.regNumber = regNumber;
        this.vehicleType = vehicleType;
        serviceAndCost = new TreeMap<Service, Integer>();
        int cost;
        for(Service s: selectedServices){
            cost = getCost(s, vehicleType);
            serviceAndCost.put(s, cost);
            totalCost += cost;
        }
        departureTime = "";
    }

    /**
     * sets the arrival time of the client
     * @param now the time that the client arrived
     */
    public void setArrivvalTime(LocalDateTime now){
        arrivalTime = now.format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }

    /**
     * returns the arrival time of the client
     * @return the arrival time of the client
     */
    public String getArrivalTime(){
        return arrivalTime;
    }
    /**
     * sets the departure time of the client
     */
    public void setDepartureTime(LocalDateTime now){
        departureTime = now.format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }

    /**
     * returns the departure time of the client
     * @return the departure time of the client
     */
    public String getDepartureTime(){
        return departureTime;
    }

    /**
     * returns the total cost of the services that the client has selected
     * @return the total cost of the services that the client has selected
     */
    public int getTotalCost(){
        return totalCost;
    }
    /**
     * returns the type of the vehicle (Αυτοκίνητο, Τζίπ, Μοτοσυκλέτα)
     * @return the type of the vehicle
     */
    public String getVehicleType(){
        return vehicleType;
    }
    /**
     * returns the registration number of the vehicle
     * @return the registration number of the vehicle
     */
    public String getregNumber(){
        return regNumber;
    }

    /**
     * returns the services that the client has selected
     * @return the services that the client has selected
     */
    public Set<Service> getSelectedServices(){
        return serviceAndCost.keySet();
    }

    /**
     * returns the cost of a service
     * @param s the service
     * @return the cost of the service
     */
    public int getServiceCost(Service s){
        return serviceAndCost.get(s);
    }

    /**
     * returns the cost of a service
     * @param s the service
     * @param vehicleType the type of the vehicle
     * @return the cost of the service
     */
    private int getCost(Service s, String vehicleType) {
        switch (vehicleType) {
            case "Αυτοκίνητο":
                return s.getCarCost();
            case "Τζίπ":
                return s.getJeepCost();
            default:
                return s.getMotorcycleCost();
        }
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){return false;}
        if(obj==this){return true;}
        if(obj instanceof ClientInfo == false){return false;}
        ClientInfo c = (ClientInfo)obj;
        if(this.regNumber.equalsIgnoreCase(c.regNumber) && this.arrivalTime.equalsIgnoreCase(c.arrivalTime)){//regNumber is unique, same client can't arrive twice at the same time
            return true;
        }
        return false;
    }
}
