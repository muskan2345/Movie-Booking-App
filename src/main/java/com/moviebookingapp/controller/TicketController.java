package com.moviebookingapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.moviebookingapp.Service.TicketService;
import com.moviebookingapp.exception.MovieNotAvailableException;
import com.moviebookingapp.exception.NoTicketBookedException;
import com.moviebookingapp.models.Tickets;
//import com.moviebookingapp.kafka.Producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class TicketController {
	
	 Logger logger = LoggerFactory.getLogger(TicketController.class);
	@Autowired
	private TicketService ticketService;
//	@Autowired
//	Producer producer;
	
	@PostMapping("/book")
	@PreAuthorize("hasRole('User')")
	public ResponseEntity<?> bookTickets(@Valid @RequestBody Tickets ticket) throws MovieNotAvailableException{
		logger.info("getting information to book ticket");
		String status=ticketService.bookTickets(ticket);
		//producer.sendMessage(ticket.getMovie().getKey().getMovieName());
		logger.info("ticket booked successfully");
		return new ResponseEntity<>(status,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/bookedtickets/{movieName}/{theatreName}")
	@PreAuthorize("hasRole('User')")
	public ResponseEntity<?> viewBookedTickets(@PathVariable String movieName,@PathVariable String theatreName) throws NoTicketBookedException{
		logger.info("getting information to view ticket");
		int msg = ticketService.viewBookedTickets(movieName,theatreName);
		return new ResponseEntity<Integer>(msg,HttpStatus.OK);
	}


}
