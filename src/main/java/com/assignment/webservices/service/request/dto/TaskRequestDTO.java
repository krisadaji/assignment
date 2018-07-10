package com.assignment.webservices.service.request.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="request")
public class TaskRequestDTO
{
	public String subject;
	public String content;
	public String status;
	
	public String getSubject()
	{
		return subject;
	}
	
	public void setSubject( String subject )
	{
		this.subject = subject;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent( String content )
	{
		this.content = content;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus( String status )
	{
		this.status = status;
	}
}
