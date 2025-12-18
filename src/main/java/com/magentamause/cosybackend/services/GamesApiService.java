package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.configs.GamesApiConfig;
import com.magentamause.cosybackend.dtos.entitydtos.GameDto;
import com.magentamause.cosybackend.dtos.gamesapi.GamesApiGamesResponse;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(GamesApiConfig.class)
public class GamesApiService {

    private final GamesApiConfig gamesApiConfig;

    public List<GameDto> queryGames(String query) {
        WebClient client =
                WebClient.builder()
                        .baseUrl(gamesApiConfig.getUrl())
                        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .build();

        GamesApiGamesResponse response =
                client.get()
                        .uri(
                                uriBuilder ->
                                        uriBuilder
                                                .path("/games")
                                                .queryParam("query", query)
                                                .queryParam("include_hero", "true")
                                                .queryParam("include_logo", "true")
                                                .build())
                        .retrieve()
                        .bodyToMono(GamesApiGamesResponse.class)
                        .block();

        if (response == null
                || response.getData() == null
                || response.getData().getGames() == null) {
            return Collections.emptyList();
        }

        return response.getData().getGames();
    }
}
