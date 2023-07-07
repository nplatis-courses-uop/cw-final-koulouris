package gr.uop;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Services {
    private Map<ServiceInfo, Cost> services;
    private String[] names = {"Πλύσιμο εξωτερικό", "Πλύσιμο εσωτερικό", "Πλύσιμο εξωτ.+εσωτ.", "Πλύσιμο εξωτ. σπέσιαλ", "Πλύσιμο εσωτ. σπέσιαλ",
    "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ", "Βιολογικός καθαρισμός εσωτ.", "Κέρωμα-Γυάλισμα", "Καθαρισμός κινητήρα", "Πλύσιμο σασί"
};
    
    public Services(){
        services = new TreeMap<>();
        ServiceInfo si[] = new ServiceInfo[10];
        for(int i = 0; i < 10; i++){
            si[i] = new ServiceInfo(i, names[i]);
        }
        services.put(si[0], new Cost(7, 8, 6));
        services.put(si[1], new Cost(6, 7));
        services.put(si[2], new Cost(12, 14));
        services.put(si[3], new Cost(9, 10, 8));
        services.put(si[4], new Cost(8, 9));
        services.put(si[5], new Cost(15, 17));
        services.put(si[6], new Cost(80, 80));
        services.put(si[7], new Cost(80, 90, 40));
        services.put(si[8], new Cost(20, 20, 10));
        services.put(si[0], new Cost(3, 3));
    }

    public String[] getServiceNames(){
        Set<ServiceInfo> serviceSet = services.keySet();
        ArrayList<String> serviceNames = new ArrayList<>();
        for(ServiceInfo si: serviceSet){
            serviceNames.add(si.name);
        }
        return (String[])serviceNames.toArray();
    }
    public int getServiceCode(String serviceName){
        int res = -1;//no service with given serviceName
        Map.Entry<ServiceInfo, Cost> e = getService(serviceName);
        if(e != null){res = e.getKey().code;}
        return res;
    }
    //if the below return -1 it means the service isn't available for corresponding type of vehicle
    public int getCarCost(String serviceName){
        int res = -1;
        Map.Entry<ServiceInfo, Cost> e = getService(serviceName);
        if(e != null){
            res = e.getValue().carCost;
        }
        return res;
    }
    public int getJeepCost(String serviceName){
        int res = -1;
        Map.Entry<ServiceInfo, Cost> e = getService(serviceName);
        if(e != null){
            res = e.getValue().jpCost;
        }
        return res;
    }
    public int getMotorcycleCost(String serviceName){
        int res = -1;
        Map.Entry<ServiceInfo, Cost> e = getService(serviceName);
        if(e != null){
            res = e.getValue().bkCost;
        }
        return res;
    }


    private Map.Entry<ServiceInfo, Cost> getService(String serviceName){
        Set<Map.Entry<ServiceInfo, Cost>> entries =  services.entrySet();
        for(Map.Entry<ServiceInfo, Cost> e: entries){
            if(e.getKey().name.equalsIgnoreCase(serviceName)){
                return e;
            }
        }
        return null;// no service with given servicename
    }

    private class Cost{
        private int carCost, jpCost, bkCost;

        private Cost(int carCost, int jpCost, int bkCost){
            this.carCost = carCost;
            this.jpCost = jpCost;
            this.bkCost = bkCost;
        }
        private Cost(int carCost, int jpCost){
            this.carCost = carCost;
            this.jpCost = jpCost;
            bkCost = -1;//service not available
        }
    }

    private class ServiceInfo implements Comparable<ServiceInfo>{
        private int code;
        private String name;

        private ServiceInfo(String name){
            this.name = name;
        }
        private ServiceInfo(int code, String name){
            this.code = code;
            this.name = name;
        }

        @Override
        public int compareTo(ServiceInfo o) {
            return this.code - o.code;
        }
    }
}
