package pl.edu.pwr.lczerwinski.websocket_simulation.sewageplant;

import java.util.Dictionary;
import java.util.Hashtable;

public class SewageService implements ISewagePlant {
    protected SewageController sewageController;
    Dictionary<Integer ,Integer> amountDictionary = new Hashtable<Integer ,Integer>();
    SewageService(SewageController sewageController)
    {
        this.sewageController = sewageController;
    }

    @Override
    public void setPumpIn(int number, int volume) {
        if(amountDictionary.get(number) != null)
        {
            amountDictionary.put(number, amountDictionary.get(number)+volume);
        }
        else
        {
            amountDictionary.put(number, volume);
        }
        sewageController.displayTankers(amountDictionary);
    }

    @Override
    public int getStatus(int number) {
        if(amountDictionary.get(number) != null)
        {
            return amountDictionary.get(number);
        }
        return 0;
    }

    @Override
    public void setPayoff(int number) {
        amountDictionary.put(number, 0);
        sewageController.displayTankers(amountDictionary);
    }
}
