package com.movie.moviecatalogue.controller;

import com.movie.moviecatalogue.model.Movie;
import com.movie.moviecatalogue.service.TMDbService;
import com.movie.moviecatalogue.service.FavoriteMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private TMDbService tmdbService;

    @Autowired
    private FavoriteMovieService favoriteMovieService;


    @GetMapping("/")
    public String homePage(Model model) {
        try {
            List<Movie> trendingMovies = tmdbService.getTrendingMovies();
            model.addAttribute("movies", trendingMovies);
        } catch (Exception e) {
            e.printStackTrace(); // this will print the real cause in the terminal
            model.addAttribute("error", "Error: " + e.getMessage());
        }
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Movie> searchResults = tmdbService.searchMovies(query);
        model.addAttribute("movies", searchResults);
        model.addAttribute("query", query);
        return "index";
    }


    @GetMapping("/movies/{id}")
    public String movieDetails(@PathVariable("id") Long id, Model model) {
        Movie movie = tmdbService.getMovieDetails(id);
        boolean isFavorite = favoriteMovieService.isFavorite(id);
        model.addAttribute("movie", movie);
        model.addAttribute("isFavorite", isFavorite);
        return "movie-details";
    }
}
