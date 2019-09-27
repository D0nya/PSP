package Client;

import Client.Controllers.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/GUI.fxml"));
        Parent root = loader.load();
        GUIController controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.setTitle("Client");
        stage.setOnCloseRequest(e -> controller.ShutDown());
        stage.show();
        controller.getData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
