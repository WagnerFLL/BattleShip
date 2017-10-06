//Slightly modified interface provided by Dr.Bushey
package Battleship;

public interface ShipAction {

    public String getShipType();

    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean);

    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean);
}
