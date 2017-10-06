package Battleship;

/**
 *
 * @author Max Cowan and Sam Uribe. October 2016
 */
public class CruiserPrototype extends Ship {

    public CruiserPrototype(int IDNum) {
        super(3, IDNum);
    }

    protected CruiserPrototype(CruiserPrototype cruiserPrototype, int IDNum){
        super(3, IDNum);
    }

    @Override
    public Ship clonar(int IDNum) {
        return new CruiserPrototype(this,IDNum);
    }

    @Override
    public String getShipType() {
        return "cruiser";
    }

    @Override
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        if (row < 2 || column < 2) {
            return false;
        }
        if (horizontal) {
            for (int i = column - 3; i < column + 2; i++) {

                for (int j = row - 1; j < row + 2; j++) {
                    if (j > -1 && j < 10 && i > -1 && i < 10 && ocean.isOccupied(j, i)) {
                        return false;
                    }
                }
            }
        } else {
            for (int i = row - 3; i < row + 2; i++) {

                for (int j = column - 1; j < column + 2; j++) {

                    if (j > -1 && j < 10 && i > -1 && i < 10 && ocean.isOccupied(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void placeShipAt(int row, int column, boolean h, Ocean ocean) {
        setBowRow(row);
        setBowColumn(column);
        setHorizontal(h);
        if (h) {
            for (int i = 0; i < 3; i++) {
                ocean.setShips(row, column - i, this);
                setIndividualCoords(row, column - i);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                ocean.setShips(row - i, column, this);
                setIndividualCoords(row - i, column);
            }
        }
    }
}
