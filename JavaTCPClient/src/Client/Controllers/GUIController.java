package Client.Controllers;

import Client.Models.Book;
import com.sun.media.jfxmediaimpl.MediaDisposer;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class GUIController implements MediaDisposer.Disposable {
    public TextArea result;
    public TextField Name;
    public TextField Author;
    public TextField Year;
    public TextField Genre;
    public TextField AuthorToSearch;
    private Socket sock = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public GUIController() {
        Connect();
    }

    public void Connect()
    {
        try{
            sock = new Socket(InetAddress.getByName("127.0.0.1"), 1024);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);
        } catch (NumberFormatException | IOException e) { AlertBox(e.toString()); }
    }
    public void getData()
    {
        result.clear();
        out.println("get");
        try {
            String[] res = in.readLine().split(":");
            for (int i = 0; i < res.length; i++) {
                result.appendText(res[i] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Send() {
        try {
            Book book = new Book();
            book.Parse("0 " + Name.getText() + " " + Author.getText() + " " + Genre.getText() + " " + Year.getText());

            out.println("add " + book.toString());
            String response = in.readLine();
            if(response == null) {
                Connect();
                return;
            }
            result.appendText(response + "\n");
        } catch (Exception e) { AlertBox(e.toString()); }
    }

    public void Search()
    {
        String searchString = AuthorToSearch.getText();
        out.println("search " + searchString);
        String response = null;
        try {
            response = in.readLine();
           String[] substrng = response.split(":");
           result.appendText("Search result:\n");
            for (String str : substrng) {
                result.appendText(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ShutDown()
    {
        if (sock != null && !sock.isClosed()) { // если сокет не пустой и сокет открыт
            try {
                sock.close(); // сокет  закрывается
            } catch (IOException e) {
                AlertBox(e.toString());
            }
        }
        this.dispose();
    }
    @Override
    public void dispose() {
        try{
            if(in != null)
                in.close();
            if(out != null) {
                out.close();
            }
        } catch (IOException e) {
            AlertBox(e.toString());
        }
    }

    private void AlertBox(String content )
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(content);
        alert.showAndWait();
    }
}
