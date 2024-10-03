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

	// Map userRepository1 = new ConcurrentHashMap<>();
	private final AtomicLong idCounter = new AtomicLong();
	private final AtomicLong channelCounter = new AtomicLong();

	@GetMapping("/welcomeUser")

	public String welcomePage(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			System.out.println("reached here empty user");
			return "welcome";

		}
		model.put("user", user);
		Collection<com.coderscampus.assignment14.domain.Channels> channels = channelRepository.findAll().values();
		model.addAttribute("channels", channels);
		System.out.println("reached here welcome get controller");
		return "welcome";
	}

	@PostMapping("/welcomeUser")
	public  ResponseEntity<String> setName(HttpSession session, @RequestBody User user) {
		User existingUser = userRepository.findByName(user.getName());

		if (existingUser != null) {
			System.out.println("User already exists: " + existingUser.getName());
			System.out.println("userid is" + existingUser.getId());
			session.setAttribute("user", existingUser); // Set existing user in session
			//return "redirect:/channel/1"; // Redirect to the channel

	        // Create HttpHeaders with redirect URL
	       // HttpHeaders headers = new HttpHeaders();
	       // headers.setLocation(URI.create("/channel/1"));
	       // return new ResponseEntity<>(headers, HttpStatus.FOUND);
			 return ResponseEntity.ok("redirect:/channel/1");
		}

		user.setId(idCounter.incrementAndGet());
		userRepository.save(user);
		System.out.println("user not empty" + user.getName());
		System.out.println("user id is" + user.getId());
		session.setAttribute("user", user);

		//return "redirect:/channel/1";
		 // Redirect to channel 1 after saving the user
	   // HttpHeaders headers = new HttpHeaders();
	   // headers.setLocation(URI.create("/channel/1"));
	   // return new ResponseEntity<>(headers, HttpStatus.FOUND);
		 return ResponseEntity.ok("redirect:/channel/1");
	}

	@GetMapping("/channel/{channelId}")
	public String viewChannel(@PathVariable Long channelId, HttpSession session, ModelMap model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			System.out.println("usr is empty");
			return "redirect:/welcomeUser";
		}
//	        com.coderscampus.assignment14.domain.Channels channel = (com.coderscampus.assignment14.domain.Channels) channelRepository.findById(channelId);
//	        model.addAttribute("chanell", channel);
//	        model.addAttribute("messages", messageRepository.findByChannelId(channelId));

		return "channel";
	}

	@PostMapping("/channels/{channelId}/message")
	public ResponseEntity<Message> postMessage(@RequestBody Message message, HttpSession session) {
//	        User user = (User) session.getAttribute("user");
//	        if (user == null) {
//	            return "redirect:/welcome";
//	        }
//	        String text = payload.get("text");
//	        if (text == null || text.isEmpty()) {
//	            return "redirect:/channels/" + channelId; // Handle empty text case
//	        }
		System.out.println("reached here");
		// com.coderscampus.assignment14.domain.Channels channel =
		// channelRepository.findById(channelId);
		Message newMessage = new Message();
		newMessage.setMessage(message.getMessage());
		// newMessage.setUsername(userName);
		// newMessage.setChannelName(message.getChannelName());
		newMessage.setChannelId(message.getChannelId());
		newMessage.setUsername(message.getUsername());
		Message savedMessage = messageRepository.save(newMessage);
		System.out.println("message is " + savedMessage.getUsername() + savedMessage.getMessage());
		session.setAttribute("message", savedMessage);
		return ResponseEntity.ok(savedMessage);
	}

	@GetMapping("/channels/{channelId}/messages")
	public ResponseEntity<List<Message>> getMessages(@PathVariable String channelId) {
		System.out.println("Reached here");
		List<Message> messages = messageRepository.findByChannelId(channelId);
		System.out.println("exiting from here");
		for (Message message : messages) {
			System.out.println(message.getMessage());
		}
		return ResponseEntity.ok(messages);
	}
}
