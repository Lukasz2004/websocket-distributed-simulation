package pl.edu.pwr.lczerwinski.websocket_simulation.tanker;

import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.SocketImplementator;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.SocketUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TankerSocket implements SocketImplementator {
    TankerService tankerService;
    private String IP;
    private int port;
    private String officeIP;
    private int officePort;
    private String plantIP;
    private int plantPort;
    private SocketUtils serverSocket;

    public TankerSocket(int Port, TankerService tankerService, String officeIP, String plantIP)
    {
        try {
            this.IP= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.port = Port;
        this.tankerService = tankerService;

        String[] office = officeIP.split(":");
        this.officeIP = office[0];
        this.officePort = Integer.valueOf(office[1]);

        String[] plant = plantIP.split(":");
        this.plantIP = plant[0];
        this.plantPort = Integer.valueOf(plant[1]);

        serverSocket = new SocketUtils(this,"Tanker",port);
        serverSocket.start();
    }

    //Server responce handling
    @Override
    public String respondToRequest(String command, String[] params) {
        String response = null;
        if(command.equals("sj"))
        {
            tankerService.setJob(params[0],params[1]);
            response = String.valueOf(1);
        }
        return response;
    }

    //Client requests sender handling
    public int sendRegisterToOffice() {
        return SocketUtils.sendRequest("r:" + IP + "," + port, officeIP, officePort);
    }
    public void sendReadyToOffice() {
        SocketUtils.sendRequest("sr:" + tankerService.tankerOfficeID, officeIP, officePort);
    }
    public void emptyTankerInPlant(int id, int volume) {
        SocketUtils.sendRequest("spi:" + id + "," + volume, plantIP, plantPort);
    }
    public int emptyPump(String IP, String port) {
        return SocketUtils.sendRequest("gp:" + tankerService.getMaxAmountToPump(),IP,Integer.valueOf(port));
    }
}
