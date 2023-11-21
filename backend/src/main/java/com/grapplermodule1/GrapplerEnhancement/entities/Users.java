    package com.grapplermodule1.GrapplerEnhancement.entities;
    
    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
    import com.grapplermodule1.GrapplerEnhancement.validations.PutValidation;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotEmpty;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Size;

    import java.util.List;
    
    @Entity
    @Table(name = "users")
    public class Users {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private Long id;

        @Column(name = "name")
        @NotEmpty(groups = {PostValidation.class, PutValidation.class}, message = "Name is required")
        @Size(max = 255, message = "Name should not exceed 255 characters")
        private String name;
    
        @Column(name = "email", unique = true)
        @Email(groups = {PostValidation.class, PutValidation.class}, message = "Email should be a valid email address")
        @NotEmpty(groups = {PostValidation.class, PutValidation.class}, message = "Email is required")
        @Size(max = 255, message = "Email should not exceed 255 characters")
        private String email;
    
        @Column(name = "designation")
        @Size(max = 255, message = "Designation should not exceed 255 characters")
        @NotEmpty(groups = {PostValidation.class, PutValidation.class}, message = "Designation is required")
        private String designation;
    
        @Column(name = "password")
        @NotEmpty(groups = {PostValidation.class}, message = "Password is required")
        private String password;

        @JsonManagedReference
        @OneToMany(mappedBy = "reportingUser")
        private List<Users> subordinates;

        @JsonBackReference
        @ManyToOne
        @JoinColumn(name = "reporting_id")
        @NotNull(groups = {PostValidation.class}, message = "Reporting User ID is required")
        private Users reportingUser;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JsonManagedReference
        @NotNull(groups = {PostValidation.class}, message = "Role is required")
        private Role role;

        @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
        private List<TeamMembers> teamMembers;


//        @JsonIgnore
//        @JsonBackReference
//        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//        private List<Ticket> ticket;
//
//        @JsonIgnore
//        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//        private List<TicketAssignment> ticketAssignment;
    
        public Long getId() {
            return id;
        }
    
        public void setId(Long id) {
            this.id = id;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getEmail() {
            return email;
        }
    
        public void setEmail(String email) {
            this.email = email;
        }
    
        public String getDesignation() {
            return designation;
        }
    
        public void setDesignation(String designation) {
            this.designation = designation;
        }
    
        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            this.password = password;
        }

        public List<Users> getSubordinates() {
            return subordinates;
        }

        public void setSubordinates(List<Users> subordinates) {
            this.subordinates = subordinates;
        }

        public Users getReportingUser() {
            return reportingUser;
        }

        public void setReportingUser(Users reportingUser) {
            this.reportingUser = reportingUser;
        }

        public List<TeamMembers> getTeamMembers() {
            return teamMembers;
        }

        public void setTeamMembers(List<TeamMembers> teamMembers) {
            this.teamMembers = teamMembers;
        }

//        public List<Ticket> getTicket() {
//            return ticket;
//        }
//
//        public void setTicket(List<Ticket> ticket) {
//            this.ticket = ticket;
//        }
//
//        public List<TicketAssignment> getTicketAssignment() {
//            return ticketAssignment;
//        }
//
//        public void setTicketAssignment(List<TicketAssignment> ticketAssignment) {
//            this.ticketAssignment = ticketAssignment;
//        }

        public Role getRole() {
            return role;
        }
    
        public void setRole(Role role) {
            this.role = role;
        }

    }
