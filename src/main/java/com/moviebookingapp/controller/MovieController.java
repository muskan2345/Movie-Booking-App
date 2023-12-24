package com.moviebookingapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.Service.MovieService;
import com.moviebookingapp.exception.MovieNotAvailableException;
import com.moviebookingapp.models.Movies;
//import com.moviebookingapp.kafka.Producer;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class MovieController {
	 Logger logger = LoggerFactory.getLogger(MovieController.class);
	@Autowired
	private MovieService movieService;
//	@Autowired
//	Producer producer;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Movies> addMovies(@Valid @RequestBody Movies movie){
		logger.info("Insertion of movie is in progress");
		//producer.sendMessage(movie.getKey().getMovieName());
		Movies newMovie = movieService.addMovies(movie);
		logger.info("Movie inserted successfully");
		return new ResponseEntity<>(newMovie,HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('Admin')|| hasRole('User')")
	public ResponseEntity<List<Movies>> allMovies(){
		logger.info("List of all mmovies is fetching");
		List<Movies> movies = movieService.allMovies();
		logger.info("List of movies are fetched");
		return new ResponseEntity<List<Movies>>(movies,HttpStatus.OK);
	}
	
	@GetMapping("{/movies/search/{movieName}")
	@PreAuthorize("hasRole('Admin')|| hasRole('User')")
	public ResponseEntity<List<Movies>> searchMovies(@PathVariable String movieName) throws MovieNotAvailableException{
		logger.info("Movies are being searched by name");
		List<Movies> movies = movieService.searchByRegex(movieName);
		logger.info("Movie are displayed");
		return new ResponseEntity<List<Movies>>(movies,HttpStatus.OK);
	}
	
	@DeleteMapping("/{movieName}/{theatreName}/delete")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<?> deleteMovie(@PathVariable String movieName, @PathVariable String theatreName) throws MovieNotAvailableException{
		logger.info("Delete movie by name is in progress");
		String msg=movieService.deleteMovie(movieName,theatreName);
		logger.info("Movie deleted successfully");
		return new ResponseEntity<String>(msg,HttpStatus.OK);
		
	}
	
	@PutMapping("/{movieName}/update")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<?> updateMovie(@PathVariable String movieName, @RequestBody Movies movie) throws MovieNotAvailableException{
		logger.info("Update movie by name is in progress");
		String msg=movieService.updateMovie(movieName,movie);
		logger.info("Movie updated successfully");
		return new ResponseEntity<String>(msg,HttpStatus.OK);
		
	}




}
