import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

    public static void main(String[] args) {
        byte[] receiveData = new byte[100];
        byte[] sendData;
        try (DatagramSocket st = new DatagramSocket(1024)) {
            while (true) {
                int a, b, c;
                final double[] sum1 = {0.0};
                final double[] sum2 = {0.0};

                DatagramPacket receivePacket = new DatagramPacket(receiveData, 100);

                st.receive(receivePacket);

                String[] substr;
                String str = new String(receivePacket.getData());
                substr = str.split(" ");
                System.out.println("Message (from" + receivePacket.getAddress() + ":" + receivePacket.getPort() + "): " + substr[0] + " " + substr[1] + " " + substr[2]);

                a = Integer.parseInt(substr[0].trim(), 10);
                b = Integer.parseInt(substr[1].trim(), 10);
                c = Integer.parseInt(substr[2].trim(), 10);

                Thread t1 = new Thread(() -> {
                    for (int i = a; i <= b; i++) {
                        sum1[0] += ((double) i * ((double) i - 1.0));
                    }
                });

                Thread t2 = new Thread(() -> {
                    for (int i = b; i <= c; i++) {
                        sum2[0] += (1.0 / ((double) i + 1.0));
                    }
                });
                t1.start();
                t2.start();
                t1.join();
                t2.join();

                String res = String.valueOf(sum1[0] - sum2[0]);
                sendData = res.getBytes();

                System.out.println("result: " + res);

                InetAddress ipAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                receivePacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
                st.send(receivePacket);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}