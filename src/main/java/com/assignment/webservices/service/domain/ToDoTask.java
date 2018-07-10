package com.assignment.webservices.service.domain;

public class ToDoTask
{
	private int taskId;
	private String taskSubject;
	private String taskContent;
	private String taskStatus;
	
	public Integer getTaskId()
	{
		return taskId;
	}
	
	public void setTaskId(Integer taskId)
	{
		this.taskId = taskId;
	}
	
	public String getTaskSubject()
	{
		return taskSubject;
	}
	
	public void setTaskSubject(String taskSubject)
	{
		this.taskSubject = taskSubject;
	}
	
	public String getTaskContent()
	{
		return taskContent;
	}
	
	public void setTaskContent(String taskContent)
	{
		this.taskContent = taskContent;
	}
	
	public String getTaskStatus()
	{
		return taskStatus;
	}
	
	public void setTaskStatus(String taskStatus)
	{
		this.taskStatus = taskStatus;
	}
}
