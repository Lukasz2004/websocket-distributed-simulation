package pl.edu.pwr.lczerwinski.websocket_simulation.sewageplant;

import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.SocketImplementator;
import pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils.SocketUtils;

public class SewageSocket implements SocketImplementator {
    SewageService sewageService;
    private int port;
    private SocketUtils serverSocket;
    public SewageSocket(String IP, SewageService sewageService)
    {
        this.port = Integer.valueOf(IP);
        this.sewageService = sewageService;
        serverSocket = new SocketUtils(this,"Plant",port);
        serverSocket.start();
    }

    //Server responce handling
    @Override
    public String respondToRequest(String command, String[] params) {
        String response = null;
        if(command.equals("spi"))
        {
            sewageService.setPumpIn(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
            response = "1";
        }
        if(command.equals("gs"))
        {
            response = String.valueOf(sewageService.getStatus(Integer.parseInt(params[0])));
        }
        if(command.equals("spo"))
        {
            sewageService.setPayoff(Integer.parseInt(params[0]));
            response = "1" ;
        }
        return response;
    }

}
