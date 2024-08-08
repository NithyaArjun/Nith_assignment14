package com.coderscampus.assignment14.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.coderscampus.assignment14.domain.Channels;

@Repository

public class ChannelRepository {
	private Map<Long, Channels> channels = new HashMap<>();
	private long channelIdSequence = 1;

	public Channels save(Channels channel) {
		channel.setId(channelIdSequence++);
		channels.put(channel.getId(), channel);
		return channel;
	}

	public Channels findByChannelId(Long id) {
		return channels.get(id);
	}

	public Map<Long, Channels> findAll() {
		return channels;
	}

}
