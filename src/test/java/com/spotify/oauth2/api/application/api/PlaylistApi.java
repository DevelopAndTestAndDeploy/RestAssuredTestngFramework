package com.spotify.oauth2.api.application.api;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {

    //static String renewToken() = "BQCGypRT_xw8v03kkiAB7OS9mTX6QhzSETU2UMt7Xkb9elrmaRZTUZZ-0detLVpHtjL9SFPF8MNP6gbvIswH-fCtEksH27p9km2JSV_VyPCuwytXFSCB5tO2i_lWCBRrwmM-QrLh8xKLYt8Vk7QYXYAsToEy4aMx3aiBKfpgvnZtFFbwd5bqWJjdXJvWC0o3gWkiE4ng2C28TSvE-jShlTIpCULDh_wSUNI-oD5h715IniOu";


    @Step
    public static Response post(Playlist requestPlayList) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser()
                + PLAYLISTS, getToken(), requestPlayList);


    }


    public static Response post(String token, Playlist requestPlayList) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser()
                + PLAYLISTS, token, requestPlayList);

            }


    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());

    }


    public static Response update(String playlistId, Playlist requestPlaylist) {

        return RestResource.update(PLAYLISTS + "/" + playlistId, getToken(), requestPlaylist);

    }
}
