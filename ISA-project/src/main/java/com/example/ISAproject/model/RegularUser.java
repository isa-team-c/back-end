package com.example.ISAproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.example.ISAproject.model.enumerations.UserRole;

@Entity
public class RegularUser {
	@Id
    private Long id;


	@OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
	
	@Column(name = "penalties", nullable = false)
	private Integer penalties;
	
	
	public RegularUser() {
        
    }
	
	public RegularUser(Long id, User user, Integer penalties) {
		super();
		this.id = id;
		this.user = user;
		this.penalties = penalties;
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

	public Integer getPenalties() {
		return penalties;
	}

	public void setPenalties(Integer penalties) {
		this.penalties = penalties;
	}

	
}
