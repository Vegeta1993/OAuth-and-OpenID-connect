package com.db;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Chat")
public class Chat {
    private Integer id;
    private Integer user_id;
    private String chat;
 
    public Chat() {
 
    }
 
    public Chat(Integer id, Integer uid, String chat) {
        this.id = id;
        this.user_id = uid;
        this.chat = chat;
    }
 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}
	
}
