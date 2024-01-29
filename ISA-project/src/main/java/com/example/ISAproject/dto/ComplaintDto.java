package com.example.ISAproject.dto;

import com.example.ISAproject.model.Complaint;

public class ComplaintDto {
	private long id;
	private UserDto userDto;
	private CompanyDto companyDto;
	//private CompanyAdministratorDto companyAdministratorDto;
	private String complaintContent;
	private boolean responded;
	
	public ComplaintDto () { }
	
	public ComplaintDto(Complaint complaint) {
		this.id = complaint.getId();
		this.userDto = new UserDto(complaint.getUser());
		this.companyDto = new CompanyDto(complaint.getCompany());
		//this.companyAdministratorDto = new CompanyAdministratorDto(complaint.getCompanyAdministrator());
		this.complaintContent = complaint.getComplaintContent();
		this.responded = complaint.getResponded();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public CompanyDto getCompanyDto() {
		return companyDto;
	}

	public void setCompanyDto(CompanyDto companyDto) {
		this.companyDto = companyDto;
	}

	/*
	public CompanyAdministratorDto getCompanyAdministratorDto() {
		return companyAdministratorDto;
	}

	public void setCompanyAdministratorDto(CompanyAdministratorDto companyAdministratorDto) {
		this.companyAdministratorDto = companyAdministratorDto;
	}
	*/

	public String getComplaintContent() {
		return complaintContent;
	}

	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}

	public boolean getResponded() {
		return responded;
	}

	public void setResponded(boolean responded) {
		this.responded = responded;
	}
}
