package pl.edu.pwr.lczerwinski.websocket_simulation.office;

import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.SocketImplementator;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.SocketUtils;

public class OfficeSocket implements SocketImplementator {
    OfficeService officeService;
    private int port;
    private String plantIP;
    private int plantPort;
    private SocketUtils serverSocket;
    public OfficeSocket(String IP, OfficeService officeService, String plantIP)
    {
        this.port = Integer.valueOf(IP);
        this.officeService = officeService;
        String[] plant = plantIP.split(":");
        this.plantIP = plant[0];
        this.plantPort = Integer.valueOf(plant[1]);
        serverSocket = new SocketUtils(this,"Office",port);
        serverSocket.start();
    }

    //Server responce handling
    @Override
    public String respondToRequest(String command, String[] params) {
        String response = null;
        if(command.equals("r"))
        {
            response = String.valueOf(officeService.register(params[0],params[1]));
        }
        if(command.equals("o"))
        {
            response = String.valueOf(officeService.order(params[0],params[1]));
        }
        if(command.equals("sr"))
        {
            officeService.setReadyToServe(Integer.parseInt(params[0]));
            response = String.valueOf(1) ;
        }
        return response;
    }

    //Client requests sender handling
    public void sendTaskToTanker(String houseIP, String housePort, String tankerIP, int tankerPort) {
        SocketUtils.sendRequest("sj:" + houseIP + "," + housePort, tankerIP, tankerPort);
    }
    public int sendGetRequestToPlant(String tankerID) {
        return SocketUtils.sendRequest("gs:" + tankerID, plantIP, plantPort);
    }
    public void sendSetRequestToPlant(String tankerID) {
        SocketUtils.sendRequest("spo:" + tankerID, plantIP, plantPort);
    }

}
