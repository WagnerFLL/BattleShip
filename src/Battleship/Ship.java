package Battleship;

import java.util.ArrayList;

/**
 *
 * @author Max Cowan and Sam Uribe. October 2016
 */
public abstract class Ship implements ShipAction {

    private int bowRow;
    private int bowColumn;
    private int length;
    private int shipID;
    private boolean horizontal;
    private boolean[] hit;

    private ArrayList<Integer> individualCoords = new ArrayList<>();

    public Ship(int l, int IDNum) {
        length = l;
        shipID = IDNum;
        hit = new boolean[l];
        for (boolean i : hit) {
            i = false;
        }
    }

    public void setIndividualCoords(int row, int column) {
        individualCoords.add(row);
        individualCoords.add(column);
    }

    public int getLength() {
        return length;
    }

    public int getBowRow() {
        return bowRow;
    }

    public int getBowColumn() {
        return bowColumn;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setBowRow(int b) {
        bowRow = b;
    }

    public void setBowColumn(int b) {
        bowColumn = b;
    }

    public void setHorizontal(boolean h) {
        horizontal = h;
    }

    public int getShipID() {
        return shipID;
    }

    public ArrayList<Integer> getIndividualCoords() {
        return individualCoords;
    }

}
