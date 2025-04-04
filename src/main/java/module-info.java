module pl.edu.pwr.lczerwinski.websocket_simulation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports pl.edu.pwr.lczerwinski.websocket_simulation.house;
    opens pl.edu.pwr.lczerwinski.websocket_simulation.house to javafx.fxml;
    exports pl.edu.pwr.lczerwinski.websocket_simulation.office;
    opens pl.edu.pwr.lczerwinski.websocket_simulation.office to javafx.fxml;
    exports pl.edu.pwr.lczerwinski.websocket_simulation.sewageplant;
    opens pl.edu.pwr.lczerwinski.websocket_simulation.sewageplant to javafx.fxml;
    exports pl.edu.pwr.lczerwinski.websocket_simulation.tanker;
    opens pl.edu.pwr.lczerwinski.websocket_simulation.tanker to javafx.fxml;
}