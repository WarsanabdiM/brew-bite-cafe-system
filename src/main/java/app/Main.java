package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import app.controller.LoginController;
import app.controller.MainController;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);

        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("Brew & Bite Cafe");
        stage.setScene(scene);
        stage.show();
    }

    @Override
public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
    Parent root = loader.load();

    LoginController controller = loader.getController();
    
    MainController main = new MainController(stage);
    controller.setMainController(main);

    stage.setScene(new Scene(root));
    stage.show();
}


    public static void main(String[] args) {
        launch();
    }
}
