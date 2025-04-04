package pl.edu.pwr.lczerwinski.websocket_simulation.sewageplant;

public interface ISewagePlant {
    void setPumpIn(int number, int volume);
    int getStatus(int number);
    void setPayoff(int number);
}
