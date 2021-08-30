package sample;

public class InHouse extends Part {

    // VARIABLES //
    private int _machineID;

    // INITIALIZERS //
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this._machineID = machineID;
    }

    // GETTERS //
    public int getMachineID() {
        return _machineID;
    }

    // SETTERS //
    public void setMachineID(int _machineID) {
        this._machineID = _machineID;
    }
}
