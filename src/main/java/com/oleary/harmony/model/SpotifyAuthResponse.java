package com.oleary.harmony.model;

import lombok.Data;

@Data
public class SpotifyAuthResponse {
    private String access_token;
    private String token_type;
}
