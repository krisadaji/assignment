package com.assignment.webservices.service.dao.impl;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.assignment.webservices.service.dao.domain.Tdt;
import com.assignment.webservices.service.dao.domain.TdtRowMapper;
import com.assignment.webservices.service.domain.TaskStatus;

@Repository
public class TdtImpl
{
	private JdbcTemplate jdbcTemplate;
	
	public TdtImpl( JdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Tdt> findTdtAll()
	{
		return jdbcTemplate.query( "SELECT * FROM TDT", new TdtRowMapper() );
	}
	
	public Tdt findTdt( int id )
	{
		Tdt tdt = null;
		
		try
		{
			tdt = jdbcTemplate.queryForObject( "SELECT * FROM TDT WHERE ID=?", 
                                               new Object[] { id },
                                               new TdtRowMapper() );
		}
		catch( EmptyResultDataAccessException e ) {}
		
		return tdt;
	}
	
	public void addTdt( int id, String subject, String content )
	{
		String sql = "INSERT INTO TDT(ID, SUBJECT, CONTENT, STATUS) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update( sql, new Object[] { id, subject, content, TaskStatus.Draft.name() } );
	}
	
	public void deleteTdt( int id )
	{
		String sql = "DELETE FROM TDT WHERE ID=?";
		jdbcTemplate.update( sql, new Object[] { id } );
	}
	
	public Tdt updateTdt( int id, String subject, String content )
	{
		String sql = "UPDATE TDT SET SUBJECT='" + subject + "', CONTENT='" + content + "' WHERE ID=" + id;
		jdbcTemplate.update( sql );
		
		Tdt tdt = null;
		try
		{
			tdt = jdbcTemplate.queryForObject( "SELECT * FROM TDT WHERE ID=?", new Object[] { id }, new TdtRowMapper() );
		}
		catch ( EmptyResultDataAccessException e ) {}
		
		return tdt;
	}
	
	public Tdt updateTdtStatus( int id, String status )
	{
		String sql = "UPDATE TDT SET STATUS='" + status + "' WHERE ID=" + id;
		jdbcTemplate.update( sql );
		
		Tdt tdt = null;
		try
		{
			tdt = jdbcTemplate.queryForObject( "SELECT * FROM TDT WHERE ID=?", new Object[] { id }, new TdtRowMapper() );
		}
		catch ( EmptyResultDataAccessException e ) {}
		
		return tdt;
	}
	
	public int getLastId()
	{
		int id = 0;
		List<Integer> ids = jdbcTemplate.queryForList( "SELECT MAX(ID) AS ID FROM TDT", Integer.class );
		if ( ids != null && ids.size() > 0 )
		{
			id = ids.get( 0 );
		}
		return id;
	}
}
