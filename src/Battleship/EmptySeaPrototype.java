package Battleship;

/**
 *
 * @author Max Cowan and Sam Uribe. October 2016
 */
public class EmptySeaPrototype extends Ship {

    public EmptySeaPrototype(int IDNum) {
        super(1, IDNum);
    }

    protected EmptySeaPrototype(EmptySeaPrototype emptySeaPrototype, int IDNum){
        super(1, IDNum);
    }

    @Override
    public Ship clonar(int IDNum) {
        return new EmptySeaPrototype(this,IDNum);
    }

    @Override
    public String getShipType() {
        return "empty";
    }

    @Override
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
