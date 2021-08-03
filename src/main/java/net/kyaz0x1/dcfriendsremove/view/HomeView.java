package net.kyaz0x1.dcfriendsremove.view;

import net.kyaz0x1.dcfriendsremove.events.ButtonRemoveClickEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HomeView extends JFrame {

    private final JLabel lblEnterToken;
    private final JTextField txtToken;
    public static JButton btnRemove;

    public HomeView(){
        super("DCFriendsRemove v1.0.0");
        setSize(350, 120);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        this.lblEnterToken = new JLabel("Informe o token abaixo:");
        lblEnterToken.setBounds(100, 5, 135, 20);

        add(lblEnterToken);

        this.txtToken = new JTextField();
        txtToken.setBounds(10, 25, 315, 20);

        add(txtToken);

        this.btnRemove = new JButton("Remover");
        btnRemove.setBounds(110, 50, 100, 20);
        btnRemove.addActionListener(new ButtonRemoveClickEvent(txtToken));

        add(btnRemove);
    }

}