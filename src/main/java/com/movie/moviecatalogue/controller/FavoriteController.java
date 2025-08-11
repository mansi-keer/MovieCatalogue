package com.movie.moviecatalogue.controller;

import com.movie.moviecatalogue.service.FavoriteMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteMovieService favoriteMovieService;

    @PostMapping("/toggle/{id}")
    public String toggleFavorite(@PathVariable("id") Long id) {
        favoriteMovieService.toggleFavorite(id);
        return "redirect:/movies/" + id;
    }

    @PostMapping("/add/{id}")
    public String addFavorite(@PathVariable("id") Long id) {
        favoriteMovieService.addFavorite(id);
        return "redirect:/movies/" + id;
    }

    @PostMapping("/remove/{id}")
    public String removeFavorite(@PathVariable("id") Long id) {
        favoriteMovieService.removeFavorite(id);
        return "redirect:/favorites";
    }

    @GetMapping
    public String listFavorites(Model model) {
        model.addAttribute("favorites", favoriteMovieService.getAllFavorites());
        return "favorites"; // This should point to your Thymeleaf "favorites.html" page
    }
}
