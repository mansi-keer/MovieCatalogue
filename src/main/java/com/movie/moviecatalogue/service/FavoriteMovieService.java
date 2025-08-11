package com.movie.moviecatalogue.service;

import com.movie.moviecatalogue.model.FavoriteMovie;
import com.movie.moviecatalogue.model.Movie;
import com.movie.moviecatalogue.repository.FavoriteMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteMovieService {

    private final FavoriteMovieRepository favoriteMovieRepository;
    private final TMDbService tmdbService;

    public FavoriteMovieService(FavoriteMovieRepository favoriteMovieRepository, TMDbService tmdbService) {
        this.favoriteMovieRepository = favoriteMovieRepository;
        this.tmdbService = tmdbService;
    }

    public void addFavorite(Long movieId) {
        if (favoriteMovieRepository.existsById(movieId)) {
            return; // Already in favorites
        }

        Movie movie = tmdbService.getMovieDetails(movieId);
        if (movie == null) {
            System.err.println("Movie not found for ID: " + movieId);
            return;
        }

        FavoriteMovie fav = new FavoriteMovie(movie.getId(), movie.getTitle(), movie.getPosterPath());
        favoriteMovieRepository.save(fav);
    }

    public void toggleFavorite(Long id) {
        if (isFavorite(id)) {
            removeFavorite(id);
        } else {
            addFavorite(id);
        }
    }

    public void removeFavorite(Long movieId) {
        favoriteMovieRepository.deleteById(movieId);
    }

    public boolean isFavorite(Long movieId) {
        return favoriteMovieRepository.existsById(movieId);
    }

    public List<FavoriteMovie> getAllFavorites() {
        return favoriteMovieRepository.findAll();
    }
}
