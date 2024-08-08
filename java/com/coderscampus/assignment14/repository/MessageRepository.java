package com.coderscampus.assignment14.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.coderscampus.assignment14.domain.Message;

@Repository
public class MessageRepository {
	private final List<Message> messages = new ArrayList<>();

	public Message save(Message message) {
		messages.add(message);
		return message;
	}

	public List<Message> findByChannelId(String channelId) {
		return messages.stream().filter(msg -> msg.getChannelId().equals(channelId)).collect(Collectors.toList());
	}
}
