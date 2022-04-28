package model;

import java.io.Serializable;

public class ProductBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	int code;
	String name;
	String description;
	int price;
	int quantity;

	public ProductBean()
	{
		code = -1;
		name = "";
		description = "";
		quantity = 0;
	}

	public void setCode(int code)
	{
		this.code = code;
	}
	public int getCode()
	{
		return this.code;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getDescription()
	{
		return this.description;
	}
	
	public void setPrice(int price)
	{
		this.price = price;
	}
	public int getPrice()
	{
		return this.price;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	public int getQuantity()
	{
		return this.quantity;
	}

	@Override
	public String toString()
	{
		return name + " (" + code + "), " + price + " " + quantity + ". " + description;
	}

}
