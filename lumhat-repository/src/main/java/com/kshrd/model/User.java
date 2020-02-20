package com.kshrd.model;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import java.util.Collection;
import java.util.List;
public class User implements UserDetails {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dob;
    private String email;
    private String password;
    private Date signupDate;
    private Date modifyDate;
    private String facebookId;
    private Boolean status;
    private String school;
    private String imageUrl;
    private int totalClass;
    private List<Role> roles;
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", signupDate=" + signupDate +
                ", modifyDate=" + modifyDate +
                ", facebookId='" + facebookId + '\'' +
                ", status=" + status +
                ", school='" + school + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", roles=" + roles +
                '}';
    }
    public User(int id, String firstName, String lastName, String gender, Date dob, String email, String password,String facebookId, Boolean status, String school, String imageUrl, List<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.facebookId = facebookId;
        this.status = status;
        this.school = school;
        this.imageUrl = imageUrl;
        this.roles = roles;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getSignupDate() {
        return signupDate;
    }
    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }
    public Date getModifyDate() {
        return modifyDate;
    }
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    public String getFacebookId() {
        return facebookId;
    }
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public User() {
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public int getTotalClass() { return totalClass; }

    public void setTotalClass(int totalClass) { this.totalClass = totalClass; }
}