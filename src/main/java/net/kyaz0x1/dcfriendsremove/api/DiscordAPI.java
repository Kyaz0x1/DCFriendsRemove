package net.kyaz0x1.dcfriendsremove.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.kyaz0x1.dcfriendsremove.api.except.FriendRemoveException;
import net.kyaz0x1.dcfriendsremove.api.http.HttpResponseCode;
import net.kyaz0x1.dcfriendsremove.api.models.DiscordError;
import net.kyaz0x1.dcfriendsremove.api.models.Friend;
import net.kyaz0x1.dcfriendsremove.api.service.WebService;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public final class DiscordAPI {

    private static DiscordAPI INSTANCE;

    private final WebService webService;
    private final String API_ENDPOINT = "https://discord.com/api/v9";

    private final Gson gson;

    private DiscordAPI(){
        this.webService = WebService.getInstance();
        this.gson = new Gson();
    }

    public List<Friend> findFriends(){
        final String url = API_ENDPOINT + "/users/@me/relationships";
        final String response = webService.get(url);

        final List<Friend> friends = gson.fromJson(response, new TypeToken<List<Friend>>(){}.getType());
        return friends;
    }

    public boolean removeFriend(Friend friend) throws FriendRemoveException {
        final String url = API_ENDPOINT + "/users/@me/relationships/" + friend.getId();
        final String response = webService.delete(url, null);

        if(response.isEmpty())
            return true;

        final DiscordError error = gson.fromJson(response, DiscordError.class);
        throw new FriendRemoveException(error.getMessage());
    }

    public boolean isValidAccount(String token){
        final String url = "https://discord.com/api/v9/users/@me";
        final Request request = new Request.Builder()
                .url(url)
                .header("Authorization", token)
                .build();
        try {
            final Response response = webService.getClient().newCall(request).execute();
            return response.code() == HttpResponseCode.OK.getCode();
        }catch(IOException e) {
            return false;
        }
    }

    public static DiscordAPI getInstance(){
        if(INSTANCE == null){
            synchronized(DiscordAPI.class){
                if(INSTANCE == null){
                    INSTANCE = new DiscordAPI();
                }
            }
        }
        return INSTANCE;
    }

}