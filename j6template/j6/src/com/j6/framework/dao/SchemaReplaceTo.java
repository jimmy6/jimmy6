package com.j6.framework.dao;

public class SchemaReplaceTo
{
	private String toSchema;
	private String fromSchema;

	public SchemaReplaceTo()
	{
	}

	public String getToSchema ()
	{
		return toSchema;
	}

	public void setToSchema (String toSchema)
	{
		this.toSchema = toSchema;
	}

	public String getFromSchema ()
	{
		return fromSchema;
	}

	public void setFromSchema (String fromShema)
	{
		this.fromSchema = fromShema;
	}

}
