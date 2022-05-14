package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.application.api.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify oauth 2.0")
@Feature("Playlist api")
public class PlayListTest extends BaseTest {

    @Story("Playlist story")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("This is the description")
    @Test(description = "should able to create a playlist")
    public void shouldBeAbleToCreatePlayList() {

        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);


    }

    @Test
    public void shouldBeAbleToGetAPlayList() {

        Playlist requestPlaylist = playlistBuilder("Updated Playlist Name", "Updated playlist description", false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);

    }

    @Test
    public void shouldBeAbleToUpdatePlayList() {

        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);

    }
    @Story("Playlist story")
    @Test
    public void shouldNotBeAbleToCreatePlayListWithOutName() {

        Playlist requestPlaylist = playlistBuilder("", generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400);
        assertError(response.as(Error.class), StatusCode.CODE_400);

    }

    @Story("Playlist story")
    @Test
    public void shouldNotBeAbleToCreatePlayListWithAnExpiredToken() {
        String invalid_token = "12345";
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401);
        assertError(response.as(Error.class), StatusCode.CODE_401);


    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public) {
        return Playlist.builder().name(name).description(description)._public(false).build();

    }

    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));

    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode) {
        assertThat(actualStatusCode, equalTo(statusCode.code));

    }

    @Step
    public void assertError(Error responseError, StatusCode statusCode) {
        assertThat(responseError.getError().getStatus(), equalTo(statusCode.code));
        assertThat(responseError.getError().getMessage(), equalTo(statusCode.msg));
    }


}
