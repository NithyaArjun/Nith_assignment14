package com.coderscampus.assignment14.domain;

import java.util.List;

public class Channels {
	private Long id;
	private String channelName;
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Channels(Long id, String channelName, List<Message> messages) {
		super();
		this.id = id;
		this.channelName = channelName;
		this.messages = messages;
	}

	private List<Message> messages;

}
