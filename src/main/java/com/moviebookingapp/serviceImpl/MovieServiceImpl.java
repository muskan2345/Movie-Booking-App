package com.moviebookingapp.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebookingapp.Repository.MoviesRepo;
import com.moviebookingapp.Service.MovieService;
import com.moviebookingapp.dto.ForgotPassword;
import com.moviebookingapp.exception.MovieNotAvailableException;
import com.moviebookingapp.exception.PasswordMismatchException;
import com.moviebookingapp.exception.UserNotExistException;
import com.moviebookingapp.models.Movies;
import com.moviebookingapp.models.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService{
	
	 Logger logger = LoggerFactory.getLogger(MovieService.class);

	@Autowired
	private MoviesRepo moviesRepo;
	@Override
	public Movies addMovies(Movies movie) {
		logger.info("adding new movie details");
		return moviesRepo.save(movie);
	}
	@Override
	public List<Movies> allMovies() {
		logger.info("getting all movies details");
		List<Movies> movies= moviesRepo.findAll();
		return movies;
	}
	@Override
	public List<Movies> searchByRegex(String str) throws MovieNotAvailableException{
		logger.info("searching movie by name");
		List<Movies> movies = moviesRepo.searchByRegex(str);
		if(movies.size()==0) {
			logger.error("no movie found with "+str+" name");
			throw new MovieNotAvailableException("Searched movie is not available");
			
		}
		logger.info("movie found with "+str+" name");
		return movies;
	}
	@Override
	public String deleteMovie(String movieName, String theatreName) throws MovieNotAvailableException{
		logger.info("searching movie by name");
		Movies movie = moviesRepo.findBymovieName(movieName,theatreName);
		if(movie==null) {
			logger.error("no movie found with "+movieName+" name");
			throw new MovieNotAvailableException("Movie is not available");
		}
		else {
		moviesRepo.delete(movie);
		logger.info("movie found with "+movieName+" name deleted");
		return movieName+" deleted successfully!";
		}
	}
	
	public String updateMovie(String movieName,Movies movie) throws MovieNotAvailableException {
		logger.info("finding user");
		Movies movieNew = moviesRepo.findByMovie(movieName);
		if(movieNew==null) {
			logger.error("Movie not found");
			throw new MovieNotAvailableException("This Movie doesnot exist");
		}
		else {
		{
			logger.info("updating movie");
			movieNew.setKey(movie.getKey());
			movieNew.setTotalNoOfTickets(movie.getTotalNoOfTickets());
		
			return "Movie Updated successfully";
		}
				}
	}


}
