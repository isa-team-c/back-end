package com.example.ISAproject.dto;

import com.example.ISAproject.model.RegularUser;

public class RegularUserDto {

	private Long id;
    private UserDto user;
    private Integer penalties;

    public RegularUserDto() {
    }

    public RegularUserDto(RegularUser regularUser) {
        this.id = regularUser.getId();
        this.user = new UserDto(regularUser.getUser());
        this.penalties = regularUser.getPenalties();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Integer getPenalties() {
        return penalties;
    }

    public void setPenalties(Integer penalties) {
        this.penalties = penalties;
    }

}
