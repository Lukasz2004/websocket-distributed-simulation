package pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.awt.*;
import java.util.Optional;

public class GUI_Utils {
    public static String barOkColor = "#cef5bc";
    public static String barWarningColor = "#f5bda2";
    public static String dialogModal(String header, String content, String defaultValue)
    {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle("Sewage Manager");
        dialog.setHeaderText(header);
        dialog.setContentText(content + ":");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        else
        {
            return defaultValue;
        }
    }
    public static void infoModal(String header, String content)
    {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Sewage Manager");
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        dialog.showAndWait();
    }
}
