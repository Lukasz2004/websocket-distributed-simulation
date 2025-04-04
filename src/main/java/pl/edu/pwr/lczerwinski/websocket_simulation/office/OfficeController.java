package pl.edu.pwr.lczerwinski.websocket_simulation.office;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.GUI_Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class OfficeController extends Application {
    //Office Logic
    protected OfficeService officeService;
    public void setup()
    {
        String inputOfficePort = GUI_Utils.dialogModal("Ustawienia IP","Podaj port dla tego biura głównego","7000");
        try {
            officeIP.setText(InetAddress.getLocalHost().getHostAddress() +":"+ inputOfficePort);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String inputPlantIP = GUI_Utils.dialogModal("Ustawienia IP","Podaj IP dla oczyszczalni ścieków","127.0.0.1:7001");
        plantIP.setText(inputPlantIP);

        System.out.println("[Office] Setup completed, creating Office on IP " + officeIP.getText());

        officeService = new OfficeService(this);
        OfficeSocket officeSocketThread = new OfficeSocket(inputOfficePort, officeService, inputPlantIP);
        officeService.officeSocket = officeSocketThread;

        setupButton.setDisable(true);
    }

    //JavaFX Logic
    @FXML
    Label officeIP;
    @FXML
    Label plantIP;
    @FXML
    Button setupButton;
    @FXML
    TextArea tankersInfo;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OfficeController.class.getResource("office-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 525, 500);
        stage.setTitle("OfficeApplication");
        stage.getIcons().add(new Image(OfficeController.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public void displayTankers(ArrayList<OfficeService.TankerListEntry> tankerList)
    {
        StringBuilder display = new StringBuilder();
        for (OfficeService.TankerListEntry tankerListEntry : tankerList)
        {
            display.append("["+tankerListEntry.id+"]: (IP:" + tankerListEntry.IP+":"+tankerListEntry.port + "); Status: ");
            if(tankerListEntry.readyToServe)
            {
                display.append("Ready for a task");
            }
            else
            {
                display.append("On a job");
            }
            display.append("\n");
        }
        tankersInfo.setText(display.toString());
    }
    public void getTankersInfo()
    {
        String tankerID = GUI_Utils.dialogModal("Komunikacja z oczyszczalnią","Podaj ID cysterny do sprawdzenia","1");
        int tankerInfo = officeService.getTankerPayoff(tankerID);
        GUI_Utils.infoModal("Komunikacja z oczyszczalnią","Podana ciężarówka dostarczyła " + tankerInfo + "l");
    }
    public void resetTankersInfo()
    {
        String tankerID = GUI_Utils.dialogModal("Komunikacja z oczyszczalnią","Podaj ID cysterny do wyresetowania","1");
        officeService.resetTankerPayoff(tankerID);
    }


}