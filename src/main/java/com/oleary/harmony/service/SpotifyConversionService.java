package com.oleary.harmony.service;

import com.oleary.harmony.model.ApiModel;
import com.oleary.harmony.util.SpotifyRequestService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpotifyConversionService implements MusicConversionService {


    private SpotifyRequestService spotifyRequestService;

    public SpotifyConversionService(SpotifyRequestService spotifyRequestService) {
        this.spotifyRequestService = spotifyRequestService;
    }

    private String accessToken = "";


    @Override
    public String convertToApiModel(String url) {

        return spotifyRequestService.getTrack(url);

    }
}
