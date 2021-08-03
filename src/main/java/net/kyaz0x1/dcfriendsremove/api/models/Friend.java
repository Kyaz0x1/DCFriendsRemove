package net.kyaz0x1.dcfriendsremove.api.models;

import net.kyaz0x1.dcfriendsremove.api.models.entity.User;

public class Friend {

    private String id;
    private int type;
    private String nickname;
    private User user;

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getNickname() {
        return nickname;
    }

    public User getUser() {
        return user;
    }

}