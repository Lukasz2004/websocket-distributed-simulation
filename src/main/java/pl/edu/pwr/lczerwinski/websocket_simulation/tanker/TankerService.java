package pl.edu.pwr.lczerwinski.websocket_simulation.tanker;

import javafx.application.Platform;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.GUI_Utils;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.MathOperations;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class TankerService implements ITanker {
    protected TankerSocket tankerSocket;
    protected TankerController tankerController;

    private Queue<TankerTaskEntry> taskList = new LinkedList<TankerTaskEntry>();
    protected int tankerOfficeID;
    protected int maxTankSize;
    private int currentTankSize;
    private boolean isOnJob=false;
    public static class TankerTaskEntry {
        public String IP;
        public String  port;
        TankerTaskEntry(String IP, String port)
        {
            this.IP = IP;
            this.port = port;
        }
    }

    TankerService(int maxTankSize, TankerController tankerController)
    {
        this.taskList = new LinkedList<>();
        this.maxTankSize = maxTankSize;
        this.currentTankSize = 0;
        this.tankerController = tankerController;
    }

    protected void register()
    {
        tankerOfficeID = tankerSocket.sendRegisterToOffice();
        System.out.println("[Tanker] Registed in Office.");
        updateGUI();
    }
    @Override
    public void setJob(String host, String port) {
        taskList.add(new TankerTaskEntry(host, port));
        System.out.println("[Tanker] New job received in " + host + ":" + port);
        Platform.runLater(()-> {
            isOnJob=true;
            updateGUI();
            try {
                sleep(2*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executeJob(host,port);});
    }
    public int getMaxAmountToPump() {
        return maxTankSize-currentTankSize;
    }

    public void executeJob(String host, String port) {
        currentTankSize += tankerSocket.emptyPump(host, port);
        updateGUI();
        if (MathOperations.calculatePercentage(currentTankSize, maxTankSize) >= 0.8) {
            try {
                sleep(2*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            tankerSocket.emptyTankerInPlant(tankerOfficeID, currentTankSize);
            currentTankSize = 0;
        }
        tankerSocket.sendReadyToOffice();
        isOnJob=false;
        updateGUI();
    }
    public void updateGUI()
    {
        Platform.runLater(() -> {
            tankerController.tankerID.setText("ID: " + tankerOfficeID);
            //Tank nearly full
            if (MathOperations.calculatePercentage(currentTankSize, maxTankSize) > 0.8) {
                tankerController.tankProgressBar.setStyle("-fx-accent: " + GUI_Utils.barWarningColor);
            } else {
                tankerController.tankProgressBar.setStyle("-fx-accent: " + GUI_Utils.barOkColor);
            }
            tankerController.tankLabel.setText(currentTankSize + "l/" + maxTankSize + "l");
            tankerController.tankProgressBar.setProgress(MathOperations.calculatePercentage(currentTankSize, maxTankSize));
            if(isOnJob)
            {
                tankerController.statusField.setText("Status: On the job");
            }
            else
            {
                tankerController.statusField.setText("Status: Ready for tasks");
            }
        });
    }
}
