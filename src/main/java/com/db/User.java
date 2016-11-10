package com.db;

 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
/**
 * The persistent class for the contact database table.
 * 
 */
@Entity
@Table(name = "User")
public class User {
    private Integer id;
    private String name;
    private String email;
    private String realm;
 
    public User() {
 
    }
 
    public User(Integer id, String name, String email,String realm) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.realm = realm;
    }
 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getName() {
        return this.name;
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

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}
    
    
}