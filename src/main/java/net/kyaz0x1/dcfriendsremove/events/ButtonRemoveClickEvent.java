package net.kyaz0x1.dcfriendsremove.events;

import net.kyaz0x1.dcfriendsremove.api.DiscordAPI;
import net.kyaz0x1.dcfriendsremove.api.auth.AuthCredentials;
import net.kyaz0x1.dcfriendsremove.api.except.FriendRemoveException;
import net.kyaz0x1.dcfriendsremove.api.models.Friend;
import net.kyaz0x1.dcfriendsremove.view.HomeView;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ButtonRemoveClickEvent implements ActionListener {

    private final JTextField txtToken;
    private final DiscordAPI api;

    public ButtonRemoveClickEvent(JTextField txtToken){
        this.txtToken = txtToken;
        this.api = DiscordAPI.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String token = txtToken.getText();

        if(token.isEmpty()){
            JOptionPane.showMessageDialog(null, "Informe o token!", "DCFriendsRemove", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AuthCredentials.TOKEN = token;

        if(!isToken(token) && !api.isValidAccount(AuthCredentials.TOKEN)) {
            JOptionPane.showMessageDialog(null, "O token informado é inválido!", "DCFriendsRemove", JOptionPane.ERROR_MESSAGE);
            return;
        }

        final List<Friend> friends = api.findFriends();

        if(friends.isEmpty()){
            JOptionPane.showMessageDialog(null, "Não há nenhum amigo para remover nesse token!", "DCFriendsRemove", JOptionPane.ERROR_MESSAGE);
            return;
        }

        final int option = JOptionPane.showConfirmDialog(null, "Foram encontrados um total de " + friends.size() + " amigos neste token! Deseja removê-los?");

        if(option == JOptionPane.YES_OPTION){
            HomeView.btnRemove.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Removendo um total de " + friends.size() + " amigos...");

            final Thread thread = new Thread(() -> {
                for(Friend friend : friends){
                    try {
                        api.removeFriend(friend);
                        System.out.printf("(%s) Removendo...\n", friend.getId());
                    }catch(FriendRemoveException ex) {
                        System.err.printf("(%s) Ocorreu um erro ao remover!\n", friend.getId());
                    }
                }
            });

            thread.start();
            try {
                thread.join();
            }catch(InterruptedException ex) {
                ex.printStackTrace();
            }

            HomeView.btnRemove.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Amigos removidos com sucesso!");
        }else{
            JOptionPane.showMessageDialog(null, "Agora os amigos não serão removidos!", "DCFriendsRemove", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isToken(String value){
        return value.matches("[a-zA-Z0-9]{24}\\.[a-zA-Z0-9]{6}\\.[a-zA-Z0-9_\\-]{27}|mfa\\.[a-zA-Z0-9_\\-]{84}");
    }

}