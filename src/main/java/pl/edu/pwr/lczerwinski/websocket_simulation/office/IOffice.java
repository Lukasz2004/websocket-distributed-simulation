package pl.edu.pwr.lczerwinski.websocket_simulation.office;

public interface IOffice {
    int register(String host, String port);
    int order(String host, String port);
    void setReadyToServe(int number);
}
