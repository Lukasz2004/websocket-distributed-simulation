package pl.edu.pwr.lczerwinski.websocket_simulation.house;

public class HouseService implements IHouse {
    protected HouseSocket houseSocket;
    private int tankStatus;
    protected int maxTankStatus;
    private boolean isTankerRequested;
    HouseService(int tankSize)
    {
        this.tankStatus=0;
        this.maxTankStatus = tankSize;
        this.isTankerRequested = false;
    }

    public synchronized void changeTankStatus(int howMuch)
    {
        tankStatus=Math.max(0,Math.min(maxTankStatus,tankStatus+howMuch));
    }
    public synchronized int getTankStatus()
    {
        return tankStatus;
    }
    public void requestTanker()
    {
        if(!isTankerRequested)
        {
            isTankerRequested = true;
            int response = houseSocket.sendOrderToOffice();
            if(response==0)
            {
                isTankerRequested=false;
            }
            System.out.println("[House] Tanker requested");

        }
    }
    @Override
    public int getPumpOut(int max) {
        int pumpOut=Math.min(max,tankStatus);
        changeTankStatus(-pumpOut);
        isTankerRequested = false;
        return pumpOut;
    }
}
