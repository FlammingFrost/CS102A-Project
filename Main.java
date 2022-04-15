import structure.Lobby;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Lobby lobby = new Lobby(1000, 760);
            lobby.setVisible(true);
        });
    }
}
