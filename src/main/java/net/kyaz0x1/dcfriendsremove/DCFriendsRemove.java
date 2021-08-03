package net.kyaz0x1.dcfriendsremove;

import net.kyaz0x1.dcfriendsremove.view.HomeView;

import javax.swing.SwingUtilities;

public class DCFriendsRemove {

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new HomeView().setVisible(true));
    }

}