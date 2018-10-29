package com.coffee.entity;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    public User(String first_name, String last_name, GenderType gender, String email, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public static enum GenderType {male, female}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", length = 20)
    private String first_name;

    @Column(name = "last_name", length = 30)
    private String last_name;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column(name = "email", length = 20)
    private String email;

    @Column(name = "phone", length = 11)
    private String phone;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(first_name, user.first_name)
                .append(last_name, user.last_name)
                .append(gender, user.gender)
                .append(email, user.email)
                .append(phone, user.phone)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(first_name)
                .append(last_name)
                .append(gender)
                .append(email)
                .append(phone)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("first_name", first_name)
                .add("last_name", last_name)
                .add("gender", gender)
                .add("email", email)
                .add("phone", phone)
                .toString();
    }
}
