package com.example.ISAproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="complaints")
public class Complaint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;
	
	/*
	@OneToOne
    @MapsId
    @JoinColumn(name = "company_admin_id", nullable = true)
    private CompanyAdministrator companyAdministrator;
    */
	
	@Column(name = "complaint_content", nullable = false)
	@NotEmpty
	private String complaintContent;
	
	@Column(name = "responded", nullable = false)
	private boolean responded;
	
	
	
	public Complaint() { }
	
	public Complaint(long id, Company company, /*CompanyAdministrator companyAdministrator,*/ String complaint_content, boolean responded)
	{
		super();
		this.id = id;
		this.user = user;
		this.company = company;
		//this.companyAdministrator = companyAdministrator;
		this.complaintContent = complaint_content;
		this.responded = responded;
	}
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	/*
	public CompanyAdministrator getCompanyAdministrator() {
		return companyAdministrator;
	}

	public void setCompanyAdministrator(CompanyAdministrator companyAdministrator) {
		this.companyAdministrator = companyAdministrator;
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
