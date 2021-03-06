package com.example.sweater.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
public class Comment {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "comment can not be emty!")
    @Length(max = 2048, message = "Message too long(more then 2KB)")
    private String text;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User commentAuthor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    private Message commentedMessage;
    
    @ManyToMany
    @JoinTable(
    		name = "comment_pluses",
    		joinColumns = {@JoinColumn(name = "comment_id")},
    		inverseJoinColumns = {@JoinColumn(name = "user_id")} 
    )
    private Set<User> commentPluses = new HashSet<User>();
    
    public Comment() {
    }
	public Comment(Long id, String text, Message commentedMessage) {
		this.id = id;
		this.text = text;
		this.commentedMessage = commentedMessage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Message getCommentedMessage() {
		return commentedMessage;
	}
	public void setCommentedMessage(Message commentedMessage) {
		this.commentedMessage = commentedMessage;
	}
	public User getCommentAuthor() {
		return commentAuthor;
	}
	public void setCommentAuthor(User commentAuthor) {
		this.commentAuthor = commentAuthor;
	}
	public Set<User> getCommentPluses() {
		return commentPluses;
	}
	public void setCommentPluses(Set<User> commentPluses) {
		this.commentPluses = commentPluses;
	}
}
