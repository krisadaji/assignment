package com.assignment.webservices.service.todotask.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.webservices.service.controller.BaseAPIRestController;
import com.assignment.webservices.service.dao.domain.Tdt;
import com.assignment.webservices.service.dao.impl.TdtImpl;
import com.assignment.webservices.service.domain.TaskStatus;
import com.assignment.webservices.service.request.dto.TaskRequestDTO;
import com.assignment.webservices.service.response.ErrorResponse;
import com.assignment.webservices.service.response.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ToDoTaskController extends BaseAPIRestController
{
	@Autowired
	private TdtImpl tdtImpl;
	
	@GetMapping("/tasks")
	public ResponseEntity getTasksResponse() throws JsonProcessingException
	{
		
		List<Tdt> tdts = tdtImpl.findTdtAll();
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString( tdts );
		
		return new ResponseEntity<String>( jsonStr, HttpStatus.OK );
	}
	
	@GetMapping("/tasks/{id}")
	public ResponseEntity getTaskResponse( @PathVariable String id ) throws JsonProcessingException
	{
		Tdt tdt = tdtImpl.findTdt( Integer.parseInt( id ) );
		
		if ( tdt == null )
		{
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorId( 1 );
			errorResponse.setErrorMessage( "Task ID: " + id + " not found." );
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString( tdt );
		
		return new ResponseEntity<String>( jsonStr, HttpStatus.OK );
	}
	
	@PostMapping("/tasks")
	public ResponseEntity addTaskResponse( @RequestBody TaskRequestDTO taskRequestDTO ) throws JsonProcessingException
	{
		int newId = tdtImpl.getLastId() + 1;
		tdtImpl.addTdt( newId, taskRequestDTO.getSubject(), taskRequestDTO.getContent() );
		Tdt tdt = tdtImpl.findTdt( newId );
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString( tdt );
		
		return new ResponseEntity<String>( jsonStr, HttpStatus.OK );
	}
	
	@PostMapping("/tasks/{id}")
	public ResponseEntity editTaskResponse( @PathVariable String id, @RequestBody TaskRequestDTO taskRequestDTO ) throws JsonProcessingException
	{
		if ( id == null || id.isEmpty() )
		{
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorId( 3 );
			errorResponse.setErrorMessage( "ID is required." );
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		else if ( taskRequestDTO == null )
		{
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorId( 4 );
			errorResponse.setErrorMessage( "<request> element is required." );
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		else if ( taskRequestDTO.getSubject() == null )
		{
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorId( 5 );
			errorResponse.setErrorMessage( "Subject is required." );
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		else if ( taskRequestDTO.getContent() == null )
		{
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorId( 6 );
			errorResponse.setErrorMessage( "Content is required." );
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		else
		{
			Tdt tdt = tdtImpl.findTdt( Integer.parseInt( id ) );
			if ( tdt == null )
			{
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrorId( 1 );
				errorResponse.setErrorMessage( "Task ID: " + id + " not found." );
				return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
			}
			else
			{
				tdtImpl.updateTdt( Integer.parseInt( id ), taskRequestDTO.getSubject(), taskRequestDTO.getContent() );
				return getTaskResponse( id );
			}
		}
	}
	
	@PostMapping("/tasks/{id}/status")
	public ResponseEntity setTaskStatusResponse( @PathVariable String id, @RequestBody TaskRequestDTO taskRequestDTO ) throws JsonProcessingException
	{
		String status = taskRequestDTO.getStatus();
		
		if ( id == null || id.isEmpty() )
		{
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorId( 3 );
			errorResponse.setErrorMessage( "ID is required." );
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		else if ( status == null || status.isEmpty() )
		{
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorId( 7 );
			errorResponse.setErrorMessage( "Status is required." );
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		else
		{
			Tdt tdt = tdtImpl.findTdt( Integer.parseInt( id ) );
			if ( tdt == null )
			{
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrorId( 1 );
				errorResponse.setErrorMessage( "Task ID: " + id + " not found." );
				return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
			}
			else if ( Arrays.asList( TaskStatus.values() ).stream().noneMatch( s -> s.name().equals( status ) ) )
			{
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrorId( 8 );
				errorResponse.setErrorMessage( "Status: " + status + " is invalid." );
				return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
			}
			
			tdtImpl.updateTdtStatus( Integer.parseInt( id ), status );
			return getTaskResponse( id );
		}
	}
	
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity deleteTaskResponse( @PathVariable String id ) throws JsonProcessingException
	{
		int deletingId = Integer.parseInt( id );
		tdtImpl.deleteTdt( deletingId );
		
		Tdt tdt = tdtImpl.findTdt( deletingId );
		if ( tdt == null )
		{
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setMessage( "Task ID: " + id + " deleted." );
			return new ResponseEntity<SuccessResponse>( successResponse, HttpStatus.OK ); 
		}
		else
		{
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorId( 2 );
			errorResponse.setErrorMessage( "Task ID: " + id + " not found." );
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
	}
}
