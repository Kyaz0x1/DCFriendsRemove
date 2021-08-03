package net.kyaz0x1.dcfriendsremove.api.service;

import net.kyaz0x1.dcfriendsremove.api.auth.AuthCredentials;
import net.kyaz0x1.dcfriendsremove.api.type.MethodType;
import net.kyaz0x1.dcfriendsremove.utils.StringUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public final class WebService {

    private static WebService INSTANCE;

    private final OkHttpClient client;
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private WebService(){
        this.client = new OkHttpClient();
    }

    public String get(String url){
        return newCall(url, null, MethodType.GET);
    }

    public String post(String url, String json){
        return newCall(url, json, MethodType.POST);
    }

    public String put(String url, String json){
        return newCall(url, json, MethodType.PUT);
    }

    public String patch(String url, String json){
        return newCall(url, json, MethodType.PATCH);
    }

    public String delete(String url, String json){
        return newCall(url, json, MethodType.DELETE);
    }

    public String newCall(String url, String json, MethodType method){
        RequestBody body = null;
        if(json != null){
            body = RequestBody.create(JSON, json);
        }

        final Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Authorization", AuthCredentials.TOKEN);

        switch(method){
            case GET:
                requestBuilder.get();
                break;
            case POST:
                requestBuilder.post(body);
                break;
            case PUT:
                requestBuilder.put(body);
                break;
            case PATCH:
                requestBuilder.patch(body);
                break;
            case DELETE:
                requestBuilder.delete(body);
                break;
        }

        final Request request = requestBuilder.build();

        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        }catch(IOException e) {
            System.err.println(String.format("[WebService] Ocorreu um erro ao fazer uma requisição do tipo %s para o site! Erro: %s", method.name(), e.getMessage()));
            return StringUtils.EMPTY;
        }
    }

    public OkHttpClient getClient() {
        return client;
    }

    public static WebService getInstance(){
        if(INSTANCE == null){
            synchronized(WebService.class){
                if(INSTANCE == null){
                    INSTANCE = new WebService();
                }
            }
        }
        return INSTANCE;
    }

}