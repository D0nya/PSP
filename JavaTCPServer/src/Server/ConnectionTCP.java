package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionTCP extends Thread {
    //счетчик подключившихся клиентов
    private static int clientsAmount = 0;
    private Socket sock;
    private PrintWriter outToClient;
    private BufferedReader inFromClient;
    private Statement sq;

    ConnectionTCP(Socket connectionSocket, Statement sq)
    {
        this.sq = sq;
        sock = connectionSocket;
        sock.getLocalPort();
        sock.getPort();
    }

    public void run() {
        try {
            clientsAmount++; //количество подключившихся клиентов увеличивается на 1
            System.out.println("=======================================");
            System.out.println("Client " + clientsAmount + " connected");

            inFromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            outToClient = new PrintWriter(sock.getOutputStream(), true);

            List<Book> books = new ArrayList<>();
            String str;
            String response = "";
            String sq_get = "SELECT * FROM books_table";
            ResultSet res;
            while ((str = inFromClient.readLine()) != null)
            {
                if (str.equals("get"))
                {
                    res = sq.executeQuery(sq_get);
                    while (res.next()) {
                        books.add(
                                new Book(
                                        res.getInt("Id"),
                                        res.getString("name"),
                                        res.getString("author"),
                                        res.getString("year"),
                                        res.getString("genre")
                                ));
                    }
                    for (Book book : books) {
                        response += book.toString() + ":";
                    }
                    outToClient.println(response);
                } else if (str.contains("add")) {
                    str = str.substring(4);
                    System.out.println(str);
                    Book book = new Book();
                    book.Parse(str);

                    String sq_add = "INSERT INTO books_table VALUES" +
                            "( 0, '" + book.getName() + "', '" + book.getAuthor() +
                            "', '" + book.getYear() + "', '" + book.getGenre() + "')";
                    int r = sq.executeUpdate(sq_add);
                    res = sq.executeQuery("SELECT Id FROM books_table ORDER BY Id DESC LIMIT 1");
                    res.next();
                    book.setId(res.getInt(1));
                    outToClient.println(book.toString());
                } else if (str.contains("search")) {
                    str = str.substring(7);
                    System.out.println(str);
                    String sq_search = "SELECT * FROM books_table WHERE  author = '" + str + "'";
                    res = sq.executeQuery(sq_search);
                    response = "";

                    while(res.next())
                    {
                        response += res.getString("Id") + " ";
                        response += res.getString("name")+ " ";
                        response += res.getString("author")+ " ";
                        response += res.getString("genre")+ " ";
                        response += res.getString("year") + ":";
                    }
                    outToClient.println(response);
                }
                else
                    outToClient.println("Wrong Command!");
            }
        } catch (
                Exception e) {
            System.out.println("Error " + e.toString());
        } finally {
            try {
                assert inFromClient != null;
                inFromClient.close();
                assert outToClient != null;
                outToClient.close();
                sock.close();//закрытие сокета, выделенного для работы с подключившимся клиентом
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                System.out.println("User " + clientsAmount + " disconnected");
                clientsAmount--;
            }
        }
    }
}
