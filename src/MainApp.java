import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label welcome = new Label("Welcome to Brew-Bite Café System ☕");
        VBox layout = new VBox(20);
        layout.getChildren().add(welcome);

        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setTitle("Brew-Bite Café System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
