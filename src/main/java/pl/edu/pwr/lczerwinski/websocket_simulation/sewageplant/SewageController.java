package pl.edu.pwr.lczerwinski.websocket_simulation.sewageplant;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.GUI_Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Dictionary;
import java.util.Enumeration;

public class SewageController extends Application {
    //Sewage Logic
    public void setup()
    {
        String inputPlantPort = GUI_Utils.dialogModal("Ustawienia IP","Podaj port dla tej oczyszczalni ścieków","7001");
        try {
            plantIP.setText(InetAddress.getLocalHost().getHostAddress() +":"+ inputPlantPort);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[Plant] Setup completed, creating Plant on IP " + plantIP.getText());

        SewageService sewageService = new SewageService(this);
        SewageSocket sewageSocketThread = new SewageSocket(inputPlantPort, sewageService);

        setupButton.setDisable(true);
    }

    //JavaFX Logic
    @FXML
    Label plantIP;
    @FXML
    Button setupButton;
    @FXML
    TextArea tankersInfo;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SewageController.class.getResource("sewageplant-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 525, 500);
        stage.setTitle("SewagePlantApplication");
        stage.getIcons().add(new Image(SewageController.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public void displayTankers(Dictionary<Integer ,Integer> tankerList)
    {
        StringBuilder info = new StringBuilder();
        Enumeration<Integer> keys = tankerList.keys();
        while(keys.hasMoreElements())
        {
            Integer key = keys.nextElement();
            Integer value = tankerList.get(key);
            info.append("["+key.toString()+"]: " + value + "\n");
        }

        tankersInfo.setText(info.toString());
    }


}