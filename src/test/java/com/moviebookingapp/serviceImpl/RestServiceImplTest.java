package com.moviebookingapp.serviceImpl;

import com.moviebookingapp.Repository.MoviesRepo;
import com.moviebookingapp.controller.RestController;
import com.moviebookingapp.models.Movies;
import com.moviebookingapp.models.Role;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond.when;

public class RestServiceImplTest
{


    @Mock
    private MoviesRepo movieRepo;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private Movies movie;

    @Test
    void getAllRest() {
        List<Movies> movies= new ArrayList<>();
        movies.add(movie);
        Mockito.when(movieRepo.findAll()).thenReturn(movies);
        assertEquals(movies, movieService.allMovies());
    }



}
