package model;

/**
 * A subclass of Part for parts created internally.
 */
public class InHouse extends Part {

    // VARIABLES //
    private int _machineID;

    // INITIALIZERS //
    /**
     * The constructor method. Calls super class constructor internally.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this._machineID = machineID;
    }

    // GETTERS //
    /**
     * Returns the machineId of the inhouse machine that made the part.
     * @return The machineId of the inhouse machine that made the part.
     */
    public int getMachineID() {
        return _machineID;
    }

    // SETTERS //
    /**
     * Sets the machineId of the inhouse machine that made the part.
     */
    public void setMachineID(int _machineID) {
        this._machineID = _machineID;
    }
}
