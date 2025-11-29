module brew.bite {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens app to javafx.fxml;
    opens app.controller to javafx.fxml;
    opens app.model to com.google.gson;

    exports app;
    exports app.controller;
}

