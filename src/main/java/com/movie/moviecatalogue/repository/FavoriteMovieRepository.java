package com.movie.moviecatalogue.repository;

import com.movie.moviecatalogue.model.FavoriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {

}
