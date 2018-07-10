package com.assignment.webservices.service.domain;

public enum TaskStatus
{
	Draft
	{
		@Override
		public String toString()
		{
			return "Draft";
		}
	},
	Running
	{
		@Override
		public String toString()
		{
			return "Running";
		}
	},
	Complete
	{
		@Override
		public String toString()
		{
			return "Complete";
		}
	},
}
