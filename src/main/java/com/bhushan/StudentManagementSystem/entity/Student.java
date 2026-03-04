package com.bhushan.StudentManagementSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@Column(name = "first_name")
private String firstName;

@Column(name = "last_name")
private String lastName;

@Column(name = "email")
private String email;

// ---------------- LOCATION FIELDS (for map) ----------------

@Column(length = 500)
private String address;

private Double latitude;
private Double longitude;

// ---------------- PROFILE FIELDS ----------------

private String profileImage;   // image file path

private String phone;
private String achievement;

// IMPORTANT: "rank" is a MySQL reserved keyword
// so we renamed column safely
@Column(name = "student_rank")
private String studentRank;

// -----------------------------------------------------------

public Student() {}

// ---------- GETTERS ----------

public Integer getId() {
    return id;
}

public String getFirstName() {
    return firstName;
}

public String getLastName() {
    return lastName;
}

public String getEmail() {
    return email;
}

public String getAddress() {
    return address;
}

public Double getLatitude() {
    return latitude;
}

public Double getLongitude() {
    return longitude;
}

public String getProfileImage() {
    return profileImage;
}

public String getPhone() {
    return phone;
}

public String getAchievement() {
    return achievement;
}

public String getStudentRank() {
    return studentRank;
}

// ---------- SETTERS ----------

public void setFirstName(String firstName) {
    this.firstName = firstName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}

public void setEmail(String email) {
    this.email = email;
}

public void setAddress(String address) {
    this.address = address;
}

public void setLatitude(Double latitude) {
    this.latitude = latitude;
}

public void setLongitude(Double longitude) {
    this.longitude = longitude;
}

public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public void setAchievement(String achievement) {
    this.achievement = achievement;
}

public void setStudentRank(String studentRank) {
    this.studentRank = studentRank;
}


}
