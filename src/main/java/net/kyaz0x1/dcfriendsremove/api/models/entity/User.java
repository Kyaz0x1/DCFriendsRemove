package net.kyaz0x1.dcfriendsremove.api.models.entity;

import com.google.gson.annotations.SerializedName;

public class User {

    private String id;
    private String username;
    private String avatar;
    private int discriminator;
    @SerializedName("public_flags")
    private int publicFlags;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getDiscriminator() {
        return discriminator;
    }

    public int getPublicFlags() {
        return publicFlags;
    }

}