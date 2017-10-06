package Battleship;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;

/**
 *
 * @author Max Cowan and Sam Uribe. October 2016
 */
public class GameBoard extends JFrame {

    private Ocean ShipMap;

    public GameBoard() {

        //GUI elements of the constructor
        JFrame mainWindow = new JFrame("BattleShip");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(680, 800);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        mainWindow.setLayout(grid);
        mainWindow.setResizable(false);

        //Initializes the game logic and visuals
        ShipMap = new Ocean();

        mainWindow.add(ShipMap, c);

        mainWindow.setVisible(true);
    }
}
