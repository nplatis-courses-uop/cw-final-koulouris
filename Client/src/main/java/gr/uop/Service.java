package gr.uop;

public class Service implements Comparable<Service>{
    private String name;
    private int code;
    private Cost c;
    
    public Service(int code, String serviceName){
        this.code = code;
        name = serviceName;
        c = new Cost();
    }
    public Service(String serviceName){
        name = serviceName;
    }

    public void setCost(int carCost, int jpCost, int bkCost){
        c.setCost(carCost, jpCost, bkCost);
    }
    public void setCost(int carCost, int jpCost){
        c.setCost(carCost, jpCost);
    }
    public String getName(){
        return name;
    }

    public int getServiceCode(){
        return code;
    }
    //if the below return -1 it means the service isn't available for corresponding type of vehicle
    public int getCarCost(){
        return c.carCost;
    }
    public int getJeepCost(){
        return c.jpCost;
    }
    public int getMotorcycleCost(){
        return c.bkCost;
    }

    private class Cost{
        private int carCost, jpCost, bkCost;

        public Cost() {
        }
        public void setCost(int carCost, int jpCost) {
            this.carCost = carCost;
            this.jpCost = jpCost;
            this.bkCost = -1;//service unavailable
        }
        public void setCost(int carCost, int jpCost, int bkCost) {
            this.carCost = carCost;
            this.jpCost = jpCost;
            this.bkCost = bkCost;
        }
    }


    public boolean equals(Object obj){
        if(obj == null){return false;}
        if(obj == this){return true;}
        if(obj instanceof Service == false){return false;}
        Service s = (Service)obj;
        if(s.name.equalsIgnoreCase(this.name)){return true;}
        return false;
    }

    @Override
    public int compareTo(Service o) {
        // TODO Auto-generated method stub
        return this.code - o.code;
    }
}
