package pl.edu.pwr.lczerwinski.websocket_simulation.office;

import java.util.ArrayList;

public class OfficeService implements IOffice {
    protected OfficeSocket officeSocket;
    protected OfficeController officeController;
    public ArrayList<TankerListEntry> tankerList;
    public static class TankerListEntry {
        public int id;
        public String IP;
        public String  port;
        public boolean readyToServe;
        TankerListEntry(int id, String IP, String port, boolean readyToServe)
        {
            this.id = id;
            this.IP = IP;
            this.port = port;
            this.readyToServe = readyToServe;
        }
    }

    OfficeService(OfficeController officeController)
    {
        this.tankerList = new ArrayList<>();
        this.officeController = officeController;
    }

    @Override
    public int register(String host, String port) {
        int newId = tankerList.size()+1;
        tankerList.add(new TankerListEntry(newId,host,port,true));
        System.out.println("[Office] Tanker" + newId + " registered from " + host + ":" + port);
        officeController.displayTankers(tankerList);
        return newId;
    }

    @Override
    public int order(String host, String port) {
        System.out.println("[Office] Received cleaning order from "+ host + ":" + port);
        for(TankerListEntry tanker: tankerList)
        {
            if(tanker.readyToServe==true)
            {
                tanker.readyToServe=false;
                officeController.displayTankers(tankerList);
                officeSocket.sendTaskToTanker(host,port,tanker.IP, Integer.parseInt(tanker.port));
                System.out.println("[Office] Cleaning order sent to Tanker");

                return 1;
            }
        }
        System.out.println("[Office] Cleaning order denied. No tankers available.");
        return 0;
    }

    @Override
    public void setReadyToServe(int number) {
        for(TankerListEntry entry : tankerList)
        {
            if (entry.id==number)
            {
                entry.readyToServe=true;
                System.out.println("[Office] Tanker " + number + " is ready to serve.");
                officeController.displayTankers(tankerList);
            }
        }
    }
    public int getTankerPayoff(String tankerID)
    {
        return officeSocket.sendGetRequestToPlant(tankerID);
    }
    public void resetTankerPayoff(String tankerID)
    {
        officeSocket.sendSetRequestToPlant(tankerID);
    }
}
