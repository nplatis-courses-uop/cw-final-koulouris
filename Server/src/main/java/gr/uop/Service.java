package gr.uop;

import java.io.Serializable;

public class Service implements Comparable<Service>, Serializable{
    private String name;
    private int code;
    private Cost c;
    
    /**
     *  Constructor for services that are available for all types of vehicles
     * @param code the code of the service
     * @param serviceName the name of the service
     */
    public Service(int code, String serviceName){
        this.code = code;
        name = serviceName;
        c = new Cost();
    }

    /**
     * sets the cost for each type of vehicle
     * @param carCost the cost for a car
     * @param jpCost the cost for a jeep
     * @param bkCost the cost for a motorcycle
     */
    public void setCost(int carCost, int jpCost, int bkCost){
        c.setCost(carCost, jpCost, bkCost);
    }

    /**
     * sets the cost for cars and jeeps, motorcycles are not available for this service
     * @param carCost the cost for a car
     * @param jpCost the cost for a jeep
     */
    public void setCost(int carCost, int jpCost){
        c.setCost(carCost, jpCost);
    }
    /**
     * returns the name of the service
     * @return the name of the service
     */
    public String getName(){
        return name;
    }

    /**
     * returns the code of the service
     * @return the code of the service
     */
    public int getServiceCode(){
        return code;
    }
    /**
     * returns the cost for a car
     * @return the cost for a car
     */
    public int getCarCost(){
        return c.carCost;
    }
    /**
     * returns the cost for a jeep
     * @return the cost for a jeep
     */
    public int getJeepCost(){
        return c.jpCost;
    }
    /**
     * returns the cost for a motorcycle, -1 if service isn't available for motorcycles
     * @return the cost for a motorcycle
     */
    public int getMotorcycleCost(){
        return c.bkCost;
    }

    private class Cost implements Serializable{
        private int carCost, jpCost, bkCost;

        public Cost() {
        }
        /**
         * sets the cost for cars and jeeps, motorcycles are not available for this service
         * @param carCost the cost for a car
         * @param jpCost the cost for a jeep
         */
        public void setCost(int carCost, int jpCost) {
            this.carCost = carCost;
            this.jpCost = jpCost;
            this.bkCost = -1;//service unavailable
        }
        /**
         * sets the cost for each type of vehicle
         * @param carCost the cost for a car
         * @param jpCost the cost for a jeep
         * @param bkCost the cost for a motorcycle
         */
        public void setCost(int carCost, int jpCost, int bkCost) {
            this.carCost = carCost;
            this.jpCost = jpCost;
            this.bkCost = bkCost;
        }
    }

    @Override
    public int compareTo(Service o) {
        return this.code - o.code;
    }
}
