package com.oleary.harmony.util;


import com.google.gson.Gson;
import com.oleary.harmony.model.SpotifyApiModel;
import com.oleary.harmony.model.SpotifyAuthResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.oleary.harmony.constants.ApiConstants.*;

// https://open.spotify.com/track/42GthmiW6mIvcyaJHdMk30?si=GJHw_s2cQzyyBt1Hg-ocYA
@Service
public class SpotifyRequestService {

    private RestTemplate restTemplate = new RestTemplate();

    private String token;

    private String getToken() throws IOException {
        if (this.token != null) {
            return this.token;
        } else {
            this.token = getAccess();
            return token;
        }
    }

    private String parseTrackId(String spotifyLink) {
        return spotifyLink.substring(spotifyLink.lastIndexOf("/") + 1, spotifyLink.indexOf("?"));
    }


    public String getTrack(String spotifyUrl) {
        String trackId = parseTrackId(spotifyUrl);


        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(String.format("%s%s", SPOTIFY_URL, trackId));
            get.setHeader("Authorization", "Bearer " + getToken());
            HttpResponse response = client.execute(get);

            HttpEntity entity = response.getEntity();

            return entity != null ? EntityUtils.toString(entity) : "null";


        } catch (IOException e) {
            e.printStackTrace();
        }


        return restTemplate.getForObject(SPOTIFY_URL + "/{trackId}", String.class, trackId);
    }


    private String getAccess() throws IOException {

        String encodedAuthentication = Base64.getEncoder().encodeToString(String.format("%s:%s", SPOTIFY_CLIENT_ID, SPOTIFY_SECRET).getBytes());

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("https://accounts.spotify.com/api/token");

            post.setHeader("Authorization", "Basic " + encodedAuthentication);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "client_credentials"));
            post.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(post);


            if (response.getStatusLine().getStatusCode() == 200) {
                String responseEntity = EntityUtils.toString(response.getEntity());
                Gson gson = new Gson();

                SpotifyAuthResponse spotifyAuthResponse = gson.fromJson(responseEntity, SpotifyAuthResponse.class);


                return spotifyAuthResponse.getAccess_token();

            } else {
                return "";
            }


        }

    }
}
