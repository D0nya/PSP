package Server;

import javax.swing.plaf.nimbus.State;
import java.net.*;
import java.io.*;
import java.sql.*;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("Server Started!");
        ServerSocket serverSock;
        serverSock = new ServerSocket(1024); //создаем серверный сокет работающий локально по порту 1024
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection successful!");
            Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "qwerty");
            Statement sq = db.createStatement();

            while (true)
                new ConnectionTCP(serverSock.accept(), sq).start();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed...");
            e.printStackTrace();
        }
    }
}