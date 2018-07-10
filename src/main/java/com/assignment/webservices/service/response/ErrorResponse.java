package com.assignment.webservices.service.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="response")
public class ErrorResponse
{
	private int errorId;
	private String errorMessage;
	
	public int getErrorId()
	{
		return errorId;
	}
	
	public void setErrorId( int errorId )
	{
		this.errorId = errorId;
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
	
	public void setErrorMessage( String errorMessage )
	{
		this.errorMessage = errorMessage;
	}
}
