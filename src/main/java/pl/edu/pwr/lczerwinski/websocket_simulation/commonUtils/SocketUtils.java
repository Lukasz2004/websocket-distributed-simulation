package pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketUtils extends Thread {
    public static SocketImplementator socketImplementator;
    private String typeOfImplementator;
    private int port;
    public SocketUtils(SocketImplementator implementator, String typeOfImplementator, int port)
    {
        this.socketImplementator = implementator;
        this.typeOfImplementator = typeOfImplementator;
        this.port = port;
    }
    public void run()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("["+typeOfImplementator+"] Listening on port " + port);
            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                BufferedReader requestIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter answerOut = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = requestIn.readLine();
                String[] requestArray = request.split(":");
                String[] paramsStringArray = requestArray[1].split(",");

                String response = socketImplementator.respondToRequest(requestArray[0],paramsStringArray);

                answerOut.println(response);
                clientSocket.close();
            }
        } catch (Exception e) {
            System.out.println("Server Error: " + e);
        }
    }

    public static int sendRequest(String request, String IP, int port)
    {
        try (Socket socket = new Socket(IP, port);
             PrintWriter requestOut = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader answerIn = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            requestOut.println(request);
            return Integer.parseInt(answerIn.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
