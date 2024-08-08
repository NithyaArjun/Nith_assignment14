package com.coderscampus.assignment14.domain;

public class MessageDTO {
	 private String username;
	    private String message;

	    // Constructors
	    public MessageDTO() {}

	    public MessageDTO(String username, String message) {
	        this.username = username;
	        this.message = message;
	    }

	    // Getters and setters
	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
}
