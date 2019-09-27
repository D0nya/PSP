public class Cashier extends Thread{
    private String name;
    private int time;
    //private Queue queue;
    Integer queue;
    private int served = 0;
    private int hour = 3600;

    public Cashier(String name, int time, Integer clients)
    {
        this.name = name;
        this.time = time;
        this.queue = clients;
    }

    public String getCashierName() {return name;}
    public int getTime() {return time;}

    public void setCashierName(String name){this.name = name;}
    public void setTime(int time){this.time = time;}
    public int getServed() { return served; }
    public void run()
    {
        while(hour > 0)
        {
            queue -= 1;
            served++;
            try {
                sleep(time);
                hour -= time;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " served: " + served + " clients for 1 hour.");
    }
}
