package pl.edu.pwr.lczerwinski.websocket_simulation.house;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.GUI_Utils;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.MathOperations;

public class HouseThread extends Thread
{
    private Label tankLabel;
    private ProgressBar tankProgressBar;
    private Label statusLabel;
    private HouseService houseService;
    public HouseThread(Label tankLabel, ProgressBar tankProgressBar,Label statusLabel, HouseService houseService)
    {
        this.tankLabel = tankLabel;
        this.tankProgressBar = tankProgressBar;
        this.statusLabel = statusLabel;
        this.houseService = houseService;
    }
    public void run(){
        System.out.println("[House] Waste generating thread started");
        while(true)
        {
            if(houseService.getTankStatus()<houseService.maxTankStatus)
            {
                houseService.changeTankStatus(MathOperations.randomInt(0,3));
            }
            Platform.runLater(()->{
                //Tank nearly full
                if(MathOperations.calculatePercentage(houseService.getTankStatus(),houseService.maxTankStatus)>0.8)
                {
                    houseService.requestTanker();

                    tankProgressBar.setStyle("-fx-accent: " + GUI_Utils.barWarningColor);
                    statusLabel.setText("Cleaning Status: " + "Tanker requested");
                }
                //Tank safe
                else
                {
                    tankProgressBar.setStyle("-fx-accent: " + GUI_Utils.barOkColor);
                    statusLabel.setText("Cleaning Status: " + "Not needed");
                }
                tankLabel.setText(houseService.getTankStatus()+"l/"+houseService.maxTankStatus+"l");
                tankProgressBar.setProgress(MathOperations.calculatePercentage(houseService.getTankStatus(),houseService.maxTankStatus));
            });
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
