package Battleship;

/**
 *
 * @author Max Cowan and Sam Uribe. October 2016
 */
public class SubmarinePrototype extends Ship {

    private int shipID;

    public SubmarinePrototype(int IDNum) {
        super(1, IDNum);
    }

    protected SubmarinePrototype(SubmarinePrototype submarinePrototype, int IDNum){
        super(1, IDNum);
    }

    @Override
    public Ship clonar(int IDNum) {
        return new SubmarinePrototype(this,IDNum);
    }

    @Override
    public String getShipType() {
        return "submarine";
    }

    @Override
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        if (horizontal) {
            for (int i = column - 1; i < column + 2; i++) {

                for (int j = row - 1; j < row + 2; j++) {

                    if (j > -1 && j < 10 && i > -1 && i < 10 && ocean.isOccupied(j, i)) {
                        return false;
                    }
                }
            }
        } else {
            for (int i = row - 1; i < row + 2; i++) {

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
            for (int i = 0; i < 1; i++) {
                ocean.setShips(row, column - i, this);
                setIndividualCoords(row, column);
            }
        } else {
            for (int i = 0; i < 1; i++) {
                ocean.setShips(row - i, column, this);
                setIndividualCoords(row, column);
            }
        }
    }


}
