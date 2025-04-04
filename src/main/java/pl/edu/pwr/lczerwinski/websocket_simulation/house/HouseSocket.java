package pl.edu.pwr.lczerwinski.websocket_simulation.house;

import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.SocketImplementator;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.SocketUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HouseSocket implements SocketImplementator {
    HouseService houseService;
    private int port;
    private String officeIP;
    private int officePort;
    private SocketUtils serverSocket;
    public HouseSocket(String IP, HouseService houseService, String officeIP)
    {
        this.port = Integer.valueOf(IP);
        this.houseService = houseService;
        String[] office = officeIP.split(":");
        this.officeIP = office[0];
        this.officePort = Integer.valueOf(office[1]);
        serverSocket = new SocketUtils(this,"House",port);
        serverSocket.start();
    }

    //Server responce handling
    @Override
    public String respondToRequest(String command, String[] params) {
        String response = null;
        if(command.equals("gp"))
        {
            response = String.valueOf(houseService.getPumpOut(Integer.parseInt(params[0])));
        }
        return response;
    }

    //Client requests sender handling
    public int sendOrderToOffice() {
        try {
            return SocketUtils.sendRequest("o:" + InetAddress.getLocalHost().getHostAddress() + "," + port, officeIP, officePort);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
