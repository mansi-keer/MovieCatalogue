package com.movie.moviecatalogue.service;

import com.movie.moviecatalogue.model.Movie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class TMDbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Movie> getTrendingMovies() {
        String url = "https://api.themoviedb.org/3/trending/movie/day?api_key=" + apiKey;
        return fetchMoviesFromUrl(url);
    }

    public List<Movie> searchMovies(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + encodedQuery;
        return fetchMoviesFromUrl(url);
    }

    private List<Movie> fetchMoviesFromUrl(String url) {
        List<Movie> movies = new ArrayList<>();
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.path("results");

            if (results.isArray()) {
                for (JsonNode node : results) {
                    Movie movie = parseMovieFromJsonNode(node);
                    movies.add(movie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching movies from URL: " + url);
        }
        return movies;
    }

    public Movie getMovieDetails(Long id) {
        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + apiKey;

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode node = objectMapper.readTree(response);

            if (node.has("status_code")) {
                int statusCode = node.get("status_code").asInt();
                String statusMessage = node.has("status_message") ? node.get("status_message").asText() : "";
                System.err.println("TMDb API error: " + statusCode + " - " + statusMessage);
                return null;
            }

            return parseMovieFromJsonNode(node);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching movie details for ID: " + id);
        }

        return null;
    }

    private Movie parseMovieFromJsonNode(JsonNode node) {
        Movie movie = new Movie();
        movie.setId(node.path("id").asLong());
        movie.setTitle(node.path("title").asText(""));
        movie.setOverview(node.path("overview").asText(""));
        movie.setReleaseDate(node.path("release_date").asText(""));
        movie.setPosterPath(node.path("poster_path").asText(null));
        movie.setVoteAverage(node.path("vote_average").asDouble(0));
        return movie;
    }
}
