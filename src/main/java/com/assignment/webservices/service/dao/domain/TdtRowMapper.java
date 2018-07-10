package com.assignment.webservices.service.dao.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TdtRowMapper implements RowMapper<Tdt>
{
	@Override
	public Tdt mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Tdt tdt = new Tdt();
		tdt.setId( rs.getInt("ID") );
		tdt.setSubject( rs.getString("SUBJECT") );
		tdt.setContent( rs.getString("CONTENT") );
		tdt.setStatus( rs.getString("STATUS") );
		return tdt;
	}

}
