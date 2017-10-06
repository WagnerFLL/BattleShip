package Battleship;

/**
 *
 * @author Max Cowan and Sam Uribe. October 2016
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ocean extends JPanel {

    private Ship[][] ships;
    private JButton[][] buttons = new JButton[10][10];
    private JButton cheatToggle;
    private JButton resetGame;
    private JLabel moves;
    private GridBagLayout grid;
    private GridBagConstraints constraints;
    private String[] gridLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private String[] gridNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private int shotsFired = 0;
    private int hitCount;
    private int shipsSunk;
    private int moveNum = 0;
    private int toggleNum = 0;
    private ActionListener ButtonListener;
    private EmptySeaPrototype emptySeaPrototype = new EmptySeaPrototype(0);
    private CruiserPrototype cruiserPrototype = new CruiserPrototype(0);
    private BattleshipPrototype battleshipPrototype = new BattleshipPrototype(0);
    private DestroyerPrototype destroyerPrototype = new DestroyerPrototype(0);
    private SubmarinePrototype submarinePrototype = new SubmarinePrototype(0);

    public Ocean() {

        //GUI Elements
        JPanel Ocean = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Ocean.setPreferredSize(new Dimension(650, 750));

        grid = new GridBagLayout();
        constraints = new GridBagConstraints();
        Ocean.setLayout(grid);

        //Listener initialized
        ButtonListener = (ActionEvent e) -> {

            if (e.getActionCommand().equals("reset") | e.getActionCommand().equals("cheat")) {
                if (e.getActionCommand().equals("reset")) {
                    resetGame(1);
                } else if (e.getActionCommand().equals("cheat")) {
                    toggleCheatMode();
                }
            } else {
                int xCoord = Character.getNumericValue(e.getActionCommand().charAt(0));
                int yCoord = Character.getNumericValue(e.getActionCommand().charAt(1));

                moveNum++;
                moves.setText("Moves: " + moveNum);

                if (isOccupied(xCoord, yCoord)) {
                    setHit(xCoord, yCoord);
                    if (checkSunk(xCoord, yCoord)) {
                        shipsSunk++;
                        setShipImages(xCoord, yCoord);
                        switch (ships[xCoord][yCoord].getShipType()) {
                            case ("submarine"):
                                JOptionPane.showMessageDialog(this, "Sub eliminated!");
                                break;
                            case ("destroyer"):
                                JOptionPane.showMessageDialog(this, "Destroyer eliminated!");
                                break;
                            case ("cruiser"):
                                JOptionPane.showMessageDialog(this, "Cruiser eliminated!");
                                break;
                            case ("battleship"):
                                JOptionPane.showMessageDialog(this, "Battleship eliminated!");
                                break;
                        }
                    }
                    if (isGameOver()) {
                        int reply = JOptionPane.showConfirmDialog(this, "Game Over! All enemy ships eliminated in "
                                + moveNum + " moves.\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            resetGame(2);
                        }
                    }
                } else {
                    setMiss(xCoord, yCoord);
                }
            }
        };

        //Creates the array of buttons and assigns the default space images
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                constraints.gridx = i + 1;
                constraints.gridy = j + 1;
                buttons[i][j] = new JButton();
                buttons[i][j].setEnabled(true);
                buttons[i][j].setPreferredSize(new Dimension(60, 60));
                String path = "resources/images/SpaceGrid/" + j + "-" + i + ".png";
                ImageIcon pict = new ImageIcon(getClass().getClassLoader().getResource(path));
                buttons[i][j].setIcon(pict);
                buttons[i][j].setBorder(BorderFactory.createEtchedBorder(0));
                buttons[i][j].addActionListener(ButtonListener);
                buttons[i][j].setActionCommand(String.valueOf(i) + String.valueOf(j));
                Ocean.add(buttons[i][j], constraints);
            }
        }

        //Creates the letters and numbers on the sides of the board
        for (int i = 1; i < 11; i++) {
            constraints.gridx = 0;
            constraints.gridy = i;
            JLabel gridNum = new JLabel(gridNumbers[i - 1]);
            Ocean.add(gridNum, constraints);
            constraints.gridx = i;
            constraints.gridy = 0;
            JLabel gridLet = new JLabel(gridLetters[i - 1]);
            Ocean.add(gridLet, constraints);
        }

        //Creates and adds the move counter below the board
        constraints.gridx = 1;
        constraints.gridy = 11;
        constraints.gridwidth = 3;
        constraints.ipady = 18;
        moves = new JLabel("Moves: 0");
        moves.setFont(new Font("Arial", Font.PLAIN, 25));
        moves.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        Ocean.add(moves, constraints);

        //Creates and adds the cheat toggle button below the board
        constraints.gridx = 4;
        constraints.gridy = 11;
        constraints.gridwidth = 4;
        constraints.ipady = 18;
        cheatToggle = new JButton("Toggle X-ray mode");
        cheatToggle.addActionListener(ButtonListener);
        cheatToggle.setActionCommand("cheat");
        cheatToggle.setFont(new Font("Arial", Font.PLAIN, 25));
        cheatToggle.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        Ocean.add(cheatToggle, constraints);

        //Creates and adds the reset button below the board
        constraints.gridx = 8;
        constraints.gridy = 11;
        constraints.gridwidth = 3;
        constraints.ipady = 18;
        resetGame = new JButton("Reset Game");
        resetGame.addActionListener(ButtonListener);
        resetGame.setActionCommand("reset");
        resetGame.setFont(new Font("Arial", Font.PLAIN, 25));
        resetGame.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        Ocean.add(resetGame, constraints);

        Ocean.setBorder(BorderFactory.createLineBorder(Color.black, 5));

        //creates 2-D array filed with EmptySea
        ships = new Ship[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ships[i][j] = new EmptySeaPrototype(0);
            }
        }

        //Prepares the ocean for gameplay
        placeAllShipsRandomly();

        add(Ocean);
    }

    //Generates random coordinates and orientations to try until all ships are placed
    public void placeAllShipsRandomly() {
        BattleshipPrototype bb = (BattleshipPrototype) battleshipPrototype.clonar(1);
        int[] shipCoords = generateOkCoords(bb, 4);
        int placementX = shipCoords[0];
        int placementY = shipCoords[1];
        boolean h = true;
        if (shipCoords[2] == 0) {
            h = false;
        }
        bb.placeShipAt(placementX, placementY, h, this);
        //battleship placed

        for (int i = 0; i < 2; i++) {
            CruiserPrototype cc = (CruiserPrototype) cruiserPrototype.clonar(i + 1);
            shipCoords = generateOkCoords(cc, 3);
            placementX = shipCoords[0];
            placementY = shipCoords[1];
            if (shipCoords[2] == 0) {
                h = false;
            } else {
                h = true;
            }
            cc.placeShipAt(placementX, placementY, h, this);
        }
        //Cruisers placed
        for (int i = 0; i < 3; i++) {
            DestroyerPrototype dd = (DestroyerPrototype) destroyerPrototype.clonar(i + 1);
            shipCoords = generateOkCoords(dd, 2);
            placementX = shipCoords[0];
            placementY = shipCoords[1];
            if (shipCoords[2] == 0) {
                h = false;
            } else {
                h = true;
            }
            dd.placeShipAt(placementX, placementY, h, this);
        }
        //Destroyers placed

        for (int i = 0; i < 4; i++) {
            SubmarinePrototype sub = (SubmarinePrototype) submarinePrototype.clonar(i + 1);
            shipCoords = generateOkCoords(sub, 1);
            placementX = shipCoords[0];
            placementY = shipCoords[1];
            if (shipCoords[2] == 0) {
                h = false;
            } else {
                h = true;
            }
            sub.placeShipAt(placementX, placementY, h, this);
        }
        //Submarines placed
    }

    public void toggleCheatMode() {
        if (toggleNum % 2 == 0) {
            showCheatMode();
        } else {
            disableCheatMode();
        }
        toggleNum++;
    }

    //Changes icon of occupied spaces to an exclamation point
    public void showCheatMode() {
        String path = "resources/images/caution.png";
        ImageIcon pict = new ImageIcon(getClass().getClassLoader().getResource(path));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (isOccupied(i, j)) {
                    buttons[i][j].setIcon(pict);
                }
            }
        }
    }

    //Sets the occupied spaces back to space tiles
    public void disableCheatMode() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (isOccupied(i, j)) {
                    String path = "resources/images/SpaceGrid/" + j + "-" + i + ".png";
                    ImageIcon pict = new ImageIcon(getClass().getClassLoader().getResource(path));
                    buttons[i][j].setIcon(pict);
                }
            }
        }
    }

    //Tests coordinates for valid ship placement
    public int[] generateOkCoords(Ship ship, int length) {
        SecureRandom random = new SecureRandom();
        int placementX, placementY;
        placementX = random.nextInt(10 - length + 1) + length - 1;
        placementY = random.nextInt(10 - length + 1) + length - 1;
        boolean h;
        if (random.nextInt(10) > 4) {
            h = true;
        } else {
            h = false;
        }
        int randomDancing = 0; //randomize the horizontal a bit
        while (!ship.okToPlaceShipAt(placementX, placementY, h, this)) {
            placementX = random.nextInt(10 - length + 1) + length - 1;
            placementY = random.nextInt(10 - length + 1) + length - 1;
            if (placementX + placementY + randomDancing % 2 == 0) {
                h = true;
            } else {
                h = false;
            }
            randomDancing++;
        }
        int[] ret = {placementX, placementY, 0};
        if (h) {
            ret[2] = 1;
        }
        return ret;
    }

    public boolean isOccupied(int row, int column) {
        if (ships[row][column].getShipType().equals("empty")) {
            return false;
        }
        return true;
    }

    //Analyzes the ship array to determine whether a ship is sunk
    public boolean checkSunk(int row, int column) {
        int tempRow;
        int tempColumn;
        int tempSum = 0;

        if (ships[row][column].getShipType().equals("submarine")) {
            return true;
        } else {
            for (int i = 0; i < ships[row][column].getIndividualCoords().size(); i += 2) {
                tempRow = ships[row][column].getIndividualCoords().get(i);
                tempColumn = ships[row][column].getIndividualCoords().get(i + 1);

                if (!buttons[tempRow][tempColumn].isEnabled()) {
                    tempSum++;
                }
            }
            switch (ships[row][column].getShipType()) {
                case ("destroyer"):
                    if (tempSum == 2) {
                        return true;
                    }
                case ("cruiser"):
                    if (tempSum == 3) {
                        return true;
                    }
                case ("battleship"):
                    if (tempSum == 4) {
                        return true;
                    }
            }
        }

        return false;
    }

    //Sets the multi-tile ship graphics for a sunken ship
    public void setShipImages(int row, int column) {
        String path = "";
        String directory = "";
        int tempRow;
        int tempColumn;
        int iterator = 1;
        switch (ships[row][column].getShipType()) {
            case ("submarine"):
                if (ships[row][column].isHorizontal()) {
                    directory = "resources/images/Ships/submarine/subV";
                } else {
                    directory = "resources/images/Ships/submarine/subH";
                }
                break;
            case ("destroyer"):
                if (ships[row][column].isHorizontal()) {
                    directory = "resources/images/Ships/destroyer/destroyerV";
                } else {
                    directory = "resources/images/Ships/destroyer/destroyerH";
                }
                break;
            case ("cruiser"):
                if (ships[row][column].isHorizontal()) {
                    directory = "resources/images/Ships/cruiser/cruiserV";
                } else {
                    directory = "resources/images/Ships/cruiser/cruiserH";
                }
                break;
            case ("battleship"):
                if (ships[row][column].isHorizontal()) {
                    directory = "resources/images/Ships/battleship/battleV";
                } else {
                    directory = "resources/images/Ships/battleship/battleH";
                }
                break;
        }
        for (int i = 0; i < ships[row][column].getIndividualCoords().size(); i += 2) {
            tempRow = ships[row][column].getIndividualCoords().get(i);
            tempColumn = ships[row][column].getIndividualCoords().get(i + 1);
            path = directory + iterator + ".png";
            ImageIcon disabIedIcon = new ImageIcon(getClass().getClassLoader().getResource(path));
            buttons[tempRow][tempColumn].setDisabledIcon(disabIedIcon);
            iterator++;
        }
    }

    //Changes tile icon and disables the button for a hit
    public void setHit(int row, int column) {
        String path = "resources/images/hit.png";
        ImageIcon pict = new ImageIcon(getClass().getClassLoader().getResource(path));
        buttons[row][column].setDisabledIcon(pict);
        buttons[row][column].setEnabled(false);
        shotsFired++;
    }

    //Changes tile icon and disables button for a miss
    public void setMiss(int row, int column) {
        String path = "resources/images/miss.png";
        ImageIcon pict = new ImageIcon(getClass().getClassLoader().getResource(path));
        buttons[row][column].setDisabledIcon(pict);
        buttons[row][column].setEnabled(false);
        shotsFired++;
    }

    //Resets the game state and randomly re-places all ships.
    public void resetGame(int mode) {
        switch (mode) {

            case (1):
                int reply = JOptionPane.showConfirmDialog(this, "Are you sure you wish to reset the entire game?", "Reset Conformation", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {

                    disableCheatMode();
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            ships[i][j] = new EmptySeaPrototype(0);
                            if (!buttons[i][j].isEnabled()) {
                                buttons[i][j].setEnabled(true);
                            }
                        }
                    }
                    shotsFired = 0;
                    hitCount = 0;
                    toggleNum = 0;
                    shipsSunk = 0;
                    moveNum = 0;
                    moves.setText("Moves: 0");
                    placeAllShipsRandomly();
                    break;
                }
            case (2):
                disableCheatMode();
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        ships[i][j] = new EmptySeaPrototype(0);
                        if (!buttons[i][j].isEnabled()) {
                            buttons[i][j].setEnabled(true);
                        }
                    }
                }
                shotsFired = 0;
                hitCount = 0;
                toggleNum = 0;
                shipsSunk = 0;
                moveNum = 0;
                moves.setText("Moves: 0");
                placeAllShipsRandomly();
                break;
        }
    }

    public void setShips(int row, int column, Ship type) {
        ships[row][column] = type;
    }

    public int getsShotsFired() {
        return shotsFired;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getShipsSunk() {
        return shipsSunk;
    }

    public boolean isGameOver() {
        if (shipsSunk >= 10) {
            return true;
        }
        return false;
    }

    public Ship[][] getShipArray() {
        return ships;
    }

}
