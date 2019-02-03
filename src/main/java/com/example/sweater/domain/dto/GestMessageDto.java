package com.example.sweater.domain.dto;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.utill.MessageHelper;

public class GestMessageDto {

    private Long id;
    private String text;
    private String tag;
    private User author;    
    private String filename;
    private Long likes;
    
	public GestMessageDto(Message message, Long likes) {
		this.id = message.getId();
		this.text = message.getText();
		this.tag = message.getTag();
		this.author = message.getAuthor();
		this.filename = message.getFilename();
		this.likes = likes;
	}
	public String getAuthorName() {
    	return MessageHelper.getAuthorName(author);
    }
	public Long getId() {
		return id;
	}
	public String getText() {
		return text;
	}
	public String getTag() {
		return tag;
	}
	public User getAuthor() {
		return author;
	}
	public String getFilename() {
		return filename;
	}
	public Long getLikes() {
		return likes;
	}
	@Override
	public String toString() {
		return "MessageDto [id=" + id + ", author=" + author + ", likes=" + likes + ", meLiked=" + "]";
	}
	
}