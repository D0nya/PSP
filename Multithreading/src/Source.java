import java.util.Scanner;

class Queue{
    private int clients;
    Queue(int amount)
    {
        setClients(amount);
    }

    public int getClients() {
        return clients;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }
}

public class Source {
    public static void main(String[] args) {
        Integer queue = 150;
        Scanner in = new Scanner(System.in);

        //System.out.println("Amount of clients: ");
        //Queue queue = new Queue(in.nextInt());

        int time1, time2, time3, time4;
        System.out.println("cashier1 time: ");
        time1 = in.nextInt();
        System.out.println("cashier2 time:  ");
        time2 = in.nextInt();
        System.out.println("cashier3 time:  ");
        time3 = in.nextInt();
        System.out.println("cashier4 time:  ");
        time4 = in.nextInt();

        Thread cash1 = new Cashier("c1", time1, queue);
        Thread cash2 = new Cashier("c2", time2, queue);
        Thread cash3 = new Cashier("c3", time3, queue);
        Thread cash4 = new Cashier("c4", time4, queue);

        cash1.start();
        cash2.start();
        cash3.start();
        cash4.start();
    }
}
