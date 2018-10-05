package com.example.voiceofmumbai.model;

public class FeedItem
{
	String id, title, content, content_type, category, ward, location_lat, location_long, status, status_note, timestamp, user_name;

	public FeedItem(String id, String title, String content, String content_type, String category, String ward, String location_lat, String location_long, String status, String status_note, String timestamp, String user_name)
	{

		this.id = id;
		this.title = title;
		this.content = content;
		this.content_type = content_type;
		this.category = category;
		this.ward = ward;
		this.location_lat = location_lat;
		this.location_long = location_long;
		this.status = status;
		this.status_note = status_note;
		this.timestamp = timestamp;
		this.user_name = user_name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getContent_type()
	{
		return content_type;
	}

	public void setContent_type(String content_type)
	{
		this.content_type = content_type;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getWard()
	{
		return ward;
	}

	public void setWard(String ward)
	{
		this.ward = ward;
	}

	public String getLocation_lat()
	{
		return location_lat;
	}

	public void setLocation_lat(String location_lat)
	{
		this.location_lat = location_lat;
	}

	public String getLocation_long()
	{
		return location_long;
	}

	public void setLocation_long(String location_long)
	{
		this.location_long = location_long;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus_note()
	{
		return status_note;
	}

	public void setStatus_note(String status_note)
	{
		this.status_note = status_note;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}
}
