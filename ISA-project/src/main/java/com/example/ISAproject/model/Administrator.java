package com.example.ISAproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Administrator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
	
	public Administrator()
	{
		
	}

	public Administrator(Long id, User user) {
		super();
		this.id = id;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
