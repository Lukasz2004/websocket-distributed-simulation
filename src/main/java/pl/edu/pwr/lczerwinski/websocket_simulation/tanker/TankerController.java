package pl.edu.pwr.lczerwinski.websocket_simulation.tanker;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.GUI_Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TankerController extends Application {

    //Tanker Logic
    public void setup()
    {
        String inputTankerPort = GUI_Utils.dialogModal("Ustawienia IP","Podaj port dla tego użytkownika Tanker","7010");
        try {
            tankerIP.setText(InetAddress.getLocalHost().getHostAddress() +":"+ inputTankerPort);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String inputOfficeIP = GUI_Utils.dialogModal("Ustawienia IP","Podaj IP dla biura głównego","127.0.0.1:7000");
        officeIP.setText(inputOfficeIP);
        String inputPlantIP = GUI_Utils.dialogModal("Ustawienia IP","Podaj IP dla oczyszczalni ścieków","127.0.0.1:7001");
        plantIP.setText(inputPlantIP);
        String tankSize = GUI_Utils.dialogModal("Ustawienia zbiornika","Podaj pojemność zbiornika","150");

        System.out.println("[Tanker] Setup completed, creating Tanker on IP " + tankerIP.getText());

        TankerService tankerService = new TankerService(Integer.parseInt(tankSize),this);
        TankerSocket tankerSocketThread = new TankerSocket(Integer.parseInt(inputTankerPort), tankerService, inputOfficeIP,inputPlantIP);
        tankerService.tankerSocket = tankerSocketThread;
        tankerService.register();
        setupButton.setDisable(true);
    }

    //JavaFX Logic
    @FXML
    Label tankerIP;
    @FXML
    Label officeIP;
    @FXML
    Label plantIP;
    @FXML
    Button setupButton;
    @FXML
    ProgressBar tankProgressBar;
    @FXML
    Label tankLabel;
    @FXML
    Label tankerID;
    @FXML
    Label statusField;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TankerController.class.getResource("tanker-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 500);
        stage.setTitle("TankerApplication");
        stage.getIcons().add(new Image(TankerService.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}