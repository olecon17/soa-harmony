package com.oleary.harmony.model;

import com.oleary.harmony.constants.SourceEnum;
import lombok.Data;

@Data
public class ApiModel {

    public ApiModel(Long id, String track) {
        this.id = id;
        this.title = track;
    }

    private Long id;
    private String title;
    private String artist;
    private String spotifyUrl;
    private String youtubeUrl;
    private SourceEnum sourceEnum;
}
