package com.assignment.webservices.service.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.webservices.service.dao.domain.Tdt;

@SpringBootTest(classes=TdtImplTestApplication.class)
@SpringJUnitJupiterConfig
public class TdtImplTest
{
	private final int ID_1 = 1;
	private final String SUBJECT_1 = "Subject 1";
	private final String CONTENT_1 = "Content 1";
	private final String STATUS_1 = "Draft";
	
	private final int ID_2 = 2;
	private final String SUBJECT_2 = "Subject 2";
	private final String CONTENT_2 = "Content 2";
	private final String STATUS_2 = "Draft";
	
	private final int ID_3 = 3;
	private final String SUBJECT_3 = "Subject 3";
	private final String CONTENT_3 = "Content 3";
	private final String STATUS_3 = "Draft";
	
	@Autowired
	private TdtImpl tdtImpl;
	
	@Test
	public void findTdtAllTest()
	{
		List<Tdt> tdts = tdtImpl.findTdtAll();
		assertNotNull( tdts );
		assertThat( tdts.size() ).isEqualTo( 3 );
		assertThat( tdts.get( 0 ).getId() ).isEqualTo( ID_1 );
		assertThat( tdts.get( 0 ).getSubject() ).isEqualTo( SUBJECT_1 );
		assertThat( tdts.get( 0 ).getContent() ).isEqualTo( CONTENT_1 );
		assertThat( tdts.get( 0 ).getStatus() ).isEqualTo( STATUS_1 );
		assertThat( tdts.get( 1 ).getId() ).isEqualTo( ID_2 );
		assertThat( tdts.get( 1 ).getSubject() ).isEqualTo( SUBJECT_2 );
		assertThat( tdts.get( 1 ).getContent() ).isEqualTo( CONTENT_2 );
		assertThat( tdts.get( 1 ).getStatus() ).isEqualTo( STATUS_2 );
		assertThat( tdts.get( 2 ).getId() ).isEqualTo( ID_3 );
		assertThat( tdts.get( 2 ).getSubject() ).isEqualTo( SUBJECT_3 );
		assertThat( tdts.get( 2 ).getContent() ).isEqualTo( CONTENT_3 );
		assertThat( tdts.get( 2 ).getStatus() ).isEqualTo( STATUS_3 );
	}
	
	@Test
	public void itShouldReturnSpecificTask()
	{
		Tdt tdt = tdtImpl.findTdt( 1 );
		assertNotNull( tdt );
		assertThat( tdt.getId() ).isEqualTo( ID_1 );
		assertThat( tdt.getSubject() ).isEqualTo( SUBJECT_1 );
		assertThat( tdt.getContent() ).isEqualTo( CONTENT_1 );
		assertThat( tdt.getStatus() ).isEqualTo( STATUS_1 );
	}
	
	@Test
	public void itShouldReturnNullIfTaskNotExist()
	{
		Tdt tdt = tdtImpl.findTdt( 99 );
		assertNull( tdt );
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void addTaskTest()
	{
		final int ID_4 = 4;
		final String SUBJECT_4 = "Subject 4";
		final String CONTENT_4 = "Content 4";
		
		tdtImpl.addTdt( ID_4, SUBJECT_4, CONTENT_4);
		
		Tdt tdt = tdtImpl.findTdt( 4 );
		assertNotNull( tdt );
		assertThat( tdt.getId() ).isEqualTo( ID_4 );
		assertThat( tdt.getSubject() ).isEqualTo( SUBJECT_4 );
		assertThat( tdt.getContent() ).isEqualTo( CONTENT_4 );
		assertThat( tdt.getStatus() ).isEqualTo( "Draft" );
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void deleteTaskTest()
	{
		tdtImpl.deleteTdt( 1 );
		Tdt tdt = tdtImpl.findTdt( 1 );
		assertNull( tdt );
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void updateTaskTest()
	{
		final String UPDATE_SUBJECT = "SUBJECT X";
		final String UPDATE_CONTENT = "CONTENT X";
		
		tdtImpl.updateTdt(ID_2, UPDATE_SUBJECT, UPDATE_CONTENT);
		Tdt tdt = tdtImpl.findTdt( ID_2 );
		assertNotNull( tdt );
		assertThat( tdt.getId() ).isEqualTo( ID_2 );
		assertThat( tdt.getSubject() ).isEqualTo( UPDATE_SUBJECT );
		assertThat( tdt.getContent() ).isEqualTo( UPDATE_CONTENT );
		assertThat( tdt.getStatus() ).isEqualTo( STATUS_2 );
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void updateTaskStatusTest()
	{
		final String UPDATE_STATUS = "Complete";
		
		tdtImpl.updateTdtStatus(ID_3, UPDATE_STATUS);
		Tdt tdt = tdtImpl.findTdt( ID_3 );
		assertNotNull( tdt );
		assertThat( tdt.getId() ).isEqualTo( ID_3 );
		assertThat( tdt.getSubject() ).isEqualTo( SUBJECT_3 );
		assertThat( tdt.getContent() ).isEqualTo( CONTENT_3 );
		assertThat( tdt.getStatus() ).isEqualTo( UPDATE_STATUS );
	}
}
