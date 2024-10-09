package com.coderscampus.assignment14.web;

import java.net.URI;
import java.nio.channels.Channels;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coderscampus.assignment14.domain.Message;
import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.repository.ChannelRepository;
import com.coderscampus.assignment14.repository.MessageRepository;
import com.coderscampus.assignment14.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChannelRepository channelRepository;

	@Autowired
	private MessageRepository messageRepository;

	private final AtomicLong idCounter = new AtomicLong();

	@GetMapping("/welcomeUser")
	public String welcomePage(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			
			return "welcome";

		}
		model.put("user", user);
		Collection<com.coderscampus.assignment14.domain.Channels> channels = channelRepository.findAll().values();
		model.addAttribute("channels", channels);
		return "welcome";
	}

	
	@PostMapping("/welcomeUser")
	public ResponseEntity<String> setName(HttpSession session, @RequestBody User user) {
		User existingUser = userRepository.findByName(user.getName());

		if (existingUser != null) {

			session.setAttribute("user", existingUser); // Set existing user in session
			return ResponseEntity.ok("redirect:/channel/1");
		}

		user.setId(idCounter.incrementAndGet());
		userRepository.save(user);
		session.setAttribute("user", user);
		return ResponseEntity.ok("redirect:/channel/1");

	}
	

	@GetMapping("/channel/{channelId}")
	public String viewChannel(@PathVariable Long channelId, HttpSession session, ModelMap model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
		
			return "redirect:/welcomeUser";

		}

		return "channel";
	}
	

	@PostMapping("/channels/{channelId}/message")
	public ResponseEntity<Message> postMessage(@RequestBody Message message, HttpSession session) {
		Message newMessage = new Message();
		newMessage.setMessage(message.getMessage());
		newMessage.setChannelId(message.getChannelId());
		newMessage.setUsername(message.getUsername());
		Message savedMessage = messageRepository.save(newMessage);
		session.setAttribute("message", savedMessage);
		return ResponseEntity.ok(savedMessage);
	}
	

	@GetMapping("/channels/{channelId}/messages")
	public ResponseEntity<List<Message>> getMessages(@PathVariable String channelId) {

		List<Message> messages = messageRepository.findByChannelId(channelId);
//		for (Message message : messages) {
//			System.out.println(message.getMessage());
//		}
		return ResponseEntity.ok(messages);
	}
}
