package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Controller {
    public TextArea result;
    public TextField a;
    public TextField b;
    public TextField c;

    public void Send() {
        try {
            DatagramSocket st = new DatagramSocket(1025);
            DatagramPacket dp;
            InetAddress loc = InetAddress.getByName("localhost");
            String send = a.getText() + " " + b.getText() + " " + c.getText();
            System.out.println(send);
            byte[] buf = send.getBytes();
            dp = new DatagramPacket( buf, send.length(), loc, 1024);
            System.out.println("Sending");
            st.send(dp);
            System.out.println("Numbers sent to " + loc + ":1024" +"\nReceiving...");
            buf = new byte[100];
            dp = new DatagramPacket(buf, buf.length);
            st.receive(dp);
            String response = new String(dp.getData());
            result.appendText(response + "\n");
            System.out.println("Received: " + response);
            st.close();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText(e.getLocalizedMessage() + "\n"  + e.toString());
            a.show();
        }
    }
}