package com.oleary.harmony.controller;


import com.oleary.harmony.model.ApiModel;
import com.oleary.harmony.service.SpotifyConversionService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SpotifyController {

    private SpotifyConversionService spotifyConversionService;

    @Autowired
    public SpotifyController(SpotifyConversionService spotifyConversionService) {
        this.spotifyConversionService = spotifyConversionService;
    }

    @RequestMapping(value = "/spotify", method = RequestMethod.GET)
    public void getSongs(@ApiParam String spotifyUrl) {
        System.out.println(spotifyUrl);
        String model = spotifyConversionService.convertToApiModel(spotifyUrl);

        System.out.println(model);
    }


}
