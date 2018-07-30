package forum.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {
	private int id;
	private String content;
	private User owner;
	private Thread thread;
	private Date dateCreated;
	private Map<String, Boolean> flags;

	public Post(int id, String content, User owner, Thread thread, Date dateCreated) {
		super();
		this.flags = new HashMap<>();
		this.id = id;
		this.content = content;
		this.owner = owner;
		this.thread = thread;
		this.dateCreated = dateCreated;
	}

	public Post() {
		this.flags = new HashMap<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Map<String, Boolean> getFlags() {
		return flags;
	}

	public void setFlags(Map<String, Boolean> flags) {
		this.flags = flags;
	}
	
	public Boolean getFlag(String name) {
		Boolean flag = this.flags.get(name);
		if (flag == null) {
			 flag = true;
		}
		return flag;
	}
	
	public void setFlag(String name, Boolean value) {
		this.flags.put(name, value);
	}
	
	public void canEdit(User user) {
		Boolean editable = false;
		
		if (user == null) {
			setFlag("canEdit", editable);
			return;
		}
		
		switch(user.getRole()) {
		case "Administrator":
			editable = true;
			break;
		case "Moderator":
			if (this.owner.getRole().equals("User")) { editable = true; }
			if (this.owner.getRole().equals("Moderator")) {
				if (this.owner.getUserName().equals(user.getUserName())) {
					editable = true;
				}
			}
			break;
		case "User":
			if (this.owner.getUserName().equals(user.getUserName())) {
				if (!this.thread.isLocked()) {
					editable = true;
				}
			}
			break;
		}
		
		setFlag("canEdit", editable);
	}
	
	public void canDelete(User user) {
		Boolean deletable = false;
		
		if (user == null) {
			setFlag("canDelete", deletable);
			return;
		}
		
		switch(user.getRole()) {
		case "Administrator":
			deletable = true;
			break;
			
		case "Moderator":
			if (this.owner.getRole().equals("User")) { deletable = true; }
			if (this.owner.getRole().equals("Moderator")) {
				if (this.owner.getUserName().equals(user.getUserName())) {
					deletable = true;
				}
			}
			break;
		case "User":
			if (this.owner.getRole().equals("User")) {
				if (this.owner.getUserName().equals(user.getUserName())) {
					deletable = true;
				}
			}
			break;
		}
		
		setFlag("canDelete", deletable);
	}
	
	public void canSee() {
		setFlag("canSee", this.thread.getFlag("canSee"));
	}
	
	public void physicalDelete(User user) {
		Boolean physical = false;
		
		if (user == null) {
			setFlag("physicalDelete", physical);
			return;
		}
		
		if (user.getRole().equals("Administrator")) {
			physical = true;
		}
		
		setFlag("physicalDelete", physical);
	}
	
	public void setFlags(User user) {
		canSee();
		canEdit(user);
		canDelete(user);
		physicalDelete(user);
	}
}
