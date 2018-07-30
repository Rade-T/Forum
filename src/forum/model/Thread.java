package forum.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import forum.dao.PostDAO;

public class Thread {
	private int id;
	private String name, description;
	private User owner;
	private boolean pinned;
	private boolean locked;
	private Forum forum;
	private Date dateCreated;
	private Map<String, Boolean> flags;
	
	public Thread() {
		this.flags = new HashMap<>();
	}

	public Thread(int id, String name, String description, User owner, boolean pinned, boolean locked, Forum forum,
			Date dateCreated) {
		super();
		this.flags = new HashMap<>();
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.pinned = pinned;
		this.locked = locked;
		this.forum = forum;
		this.dateCreated = dateCreated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescripton(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
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

	public void setFlags(Map<String, Boolean> flags) {
		this.flags = flags;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Thread [id=" + id + ", name=" + name + ", descripton=" + description + ", owner=" + owner + ", pinned="
				+ pinned + ", locked=" + locked + ", forum=" + forum + ", dateCreated=" + dateCreated + "]";
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
				if (!this.locked) {
					if (!this.forum.isLocked()) {
						editable = true;
					}
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
		}
		
		setFlag("canDelete", deletable);
	}
	
	public void canAddPost(User user) {
		Boolean addable = false;
		
		if (user == null) {
			setFlag("canAddPost", addable);
			return;
		}
		
		switch(user.getRole()) {
		case "Administrator":
			addable = true;
			break;
			
		case "Moderator":
			addable = true;
			break;
			
		case "User":
			if (!this.locked) {
				if (this.forum.getType().equals("Public") ||
					this.forum.getType().equals("Open")) {
					addable = true;
				}
			}
		}
		
		setFlag("canAddPost", addable);
	}
	
	public void canLock(User user) {
		Boolean lockable = false;
		
		if (user == null) {
			setFlag("canLock", lockable);
			return;
		}
		
		switch(user.getRole()) {
		case "Administrator":
			lockable = true;
			break;
			
		case "Moderator":
			if (this.owner.getRole().equals("User")) { lockable = true; }
			if (this.owner.getRole().equals("Moderator")) {
				if (this.owner.getUserName().equals(user.getUserName())) {
					lockable = true;
				}
			}
			break;
		}
		
		setFlag("canLock", lockable);
	}
	
	public void canPin(User user) {
		Boolean pinnable = false;
		
		if (user == null) {
			setFlag("canLock", pinnable);
			return;
		}
		
		switch(user.getRole()) {
		case "Administrator":
			pinnable = true;
			break;
			
		case "Moderator":
			if (this.owner.getRole().equals("User")) { pinnable = true; }
			if (this.owner.getRole().equals("Moderator")) {
				if (this.owner.getUserName().equals(user.getUserName())) {
					pinnable = true;
				}
			}
			break;
		}
		
		setFlag("canPin", pinnable);
	}
	
	public void canSee() {
		setFlag("canSee", this.forum.getFlag("canSee"));
	}
	
	public void physicalDelete(User user) {
		Boolean physical = false;
		
		if (user == null) {
			setFlag("physicalDelete", physical);
			return;
		}
		
		if (user.getRole().equals("Administrator")) {
			if (PostDAO.getThreadPosts(user, this).isEmpty()) {
				physical = true;
			}
		}
		
		setFlag("physicalDelete", physical);
	}
	
	public void setFlags(User user) {
		canSee();
		canEdit(user);
		canDelete(user);
		canAddPost(user);
		canLock(user);
		canPin(user);
		physicalDelete(user);
	}
}
