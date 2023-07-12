package gr.uop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ClientInfo implements Serializable{
    private int totalCost = 0;
    private Map<Service, Integer> serviceAndCost;
    private String vehicleType;
    private String regNumber;

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
    }

    public int getTotalCost(){
        return totalCost;
    }
    public String getVehicleType(){
        return vehicleType;
    }
    public String getregNumber(){
        return regNumber;
    }

    public Set<Service> getSelectedServices(){
        return serviceAndCost.keySet();
    }

    public int getServiceCost(Service s){
        return serviceAndCost.get(s);
    }

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

    public void deletClientInfo(ClientInfo info){
        totalCost = 0;
        serviceAndCost.clear();
        vehicleType = "";
        regNumber = "";
    }
}
