package com.oleary.harmony.service;

import com.oleary.harmony.constants.SourceEnum;
import org.springframework.beans.factory.annotation.Autowired;

public class ConversionServiceFactory {

    private SpotifyConversionService spotifyConversionService;
    private YoutubeConversionService youtubeConversionService;


    @Autowired
    public ConversionServiceFactory(SpotifyConversionService spotifyConversionService, YoutubeConversionService youtubeConversionService) {
        this.spotifyConversionService = spotifyConversionService;
        this.youtubeConversionService = youtubeConversionService;
    }

    public MusicConversionService getConversionService(SourceEnum sourceEnum) {
        switch (sourceEnum) {
            case YOUTUBE:
                return youtubeConversionService;
            case SPOTIFY:
                return spotifyConversionService;
            default:
                throw new RuntimeException("Unrecognized Source Enum!");
        }
    }
}
