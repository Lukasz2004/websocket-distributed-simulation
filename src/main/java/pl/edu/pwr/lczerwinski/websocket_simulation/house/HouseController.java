package pl.edu.pwr.lczerwinski.websocket_simulation.house;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.GUI_Utils;

public class HouseController extends Application {
    //House Logic
    private HouseThread houseThread;
    public void setup()
    {
        String inputHousePort = GUI_Utils.dialogModal("Ustawienia IP","Podaj port dla tego użytkownika House","7100");
        try {
            houseIP.setText(InetAddress.getLocalHost().getHostAddress() +":"+ inputHousePort);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String inputOfficeIP = GUI_Utils.dialogModal("Ustawienia IP","Podaj IP dla biura głównego","127.0.0.1:7000");
        officeIP.setText(inputOfficeIP);
        String tankSize = GUI_Utils.dialogModal("Ustawienia zbiornika","Podaj pojemność zbiornika","100");

        System.out.println("[House] Setup completed, creating House on IP " + houseIP.getText());

        HouseService houseService = new HouseService(Integer.parseInt(tankSize));
        houseThread = new HouseThread(tankLabel,tankProgressBar,statusLabel,houseService);
        houseThread.start();
        HouseSocket houseSocketThread = new HouseSocket(inputHousePort, houseService, inputOfficeIP);
        houseService.houseSocket = houseSocketThread;

        setupButton.setDisable(true);
    }

    //JavaFX Logic
    @FXML
    Label tankLabel;
    @FXML
    ProgressBar tankProgressBar;
    @FXML
    Label statusLabel;
    @FXML
    Label houseIP;
    @FXML
    Label officeIP;
    @FXML
    Button setupButton;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HouseController.class.getResource("house-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 300);
        stage.setTitle("HouseApplication");
        stage.getIcons().add(new Image(HouseController.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}