package forum.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import forum.dao.ForumDAO;
import forum.dao.ThreadDAO;

public class Forum {
	private int id;
	private String name, description;
	private User owner;
	private boolean locked;
	private Forum parentForum;
	private String type;
	private Date dateCreated;
	private List<Thread> forumThreads;
	private List<Forum> subForums;
	private Map<String, Boolean> flags;

	public Forum() {
		this.forumThreads = null;
		this.subForums = null;
		this.flags = new HashMap<>();
	}

	public Forum(int id, String name, String description, User owner, boolean locked, Forum parentForum, String type,
			Date dateCreated) {
		super();
		this.flags = new HashMap<>();
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.locked = locked;
		this.parentForum = parentForum;
		this.type = type;
		this.dateCreated = dateCreated;
	}

	public Forum(int id, String name, String description, User owner, boolean locked, Forum parentForum, String type,
			Date dateCreated, List<Thread> forumThreads, List<Forum> subForums) {
		super();
		this.flags = new HashMap<>();
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.locked = locked;
		this.parentForum = parentForum;
		this.type = type;
		this.dateCreated = dateCreated;
		this.forumThreads = forumThreads;
		this.subForums = subForums;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Forum getParentForum() {
		return parentForum;
	}

	public void setParentForum(Forum parentForum) {
		this.parentForum = parentForum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<Thread> getForumThreads() {
		return forumThreads;
	}

	public void setForumThreads(List<Thread> forumThreads) {
		this.forumThreads = forumThreads;
	}

	public List<Forum> getSubForums() {
		return subForums;
	}

	public void setSubForums(List<Forum> subForums) {
		this.subForums = subForums;
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
	
	public void setFlag(String name, Boolean flag) {
		this.flags.put(name, flag);
	}

	@Override
	public String toString() {
		return "Forum [id=" + id + ", name=" + name + ", description=" + description + ", owner=" + owner + ", locked="
				+ locked + ", parentForum=" + parentForum + ", type=" + type + ", dateCreated=" + dateCreated
				+ ", forumThreads=" + forumThreads + ", subForums=" + subForums + "]";
	}
	
	public void canEdit(User user) {
		Boolean editable = false;
		
		if (user == null) {
			setFlag("canEdit", editable);
			return;
		}
		
		if (user.getRole().equals("Administrator")) {
			editable = true;
		}
		
		setFlag("canEdit", editable);
	}
	
	public void canDelete(User user) {
		Boolean deletable = false;
		
		if (user == null) {
			setFlag("canDelete", deletable);
			return;
		}
		
		if (user.getRole().equals("Administrator")) {
			deletable = true;
		}
		
		setFlag("canDelete", deletable);
	}

	public void canAddSubForum(User user) {
		Boolean addable = false;
		
		if (user == null) {
			setFlag("canAddSubForum", addable);
			return;
		}
		
		if (user.getRole().equals("Administrator")) {
			addable = true;
		}
		
		setFlag("canAddSubForum", addable);
	}
	
	public void canAddThread(User user) {
		Boolean addable = false;
		
		if (user == null) {
			setFlag("canAddThread", addable);
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
			if (!isLocked()) {
				if (this.type.equals("Public") ||
					this.type.equals("Open")) {
					addable = true;
				}
			}
		}
		
		setFlag("canAddThread", addable);
	}
	
	public void canLock(User user) {
		Boolean lockable = false;
		
		if (user == null) {
			setFlag("canLock", lockable);
			return;
		}
		
		if (user.getRole().equals("Administrator")) {
			lockable = true;
		}
		
		setFlag("canLock", lockable);
	}
	
	public void canSee(User user) {
		Boolean visible = false;
		
		if (user == null) {
			if (this.type.equals("Public")) {
				visible = true;
			}
			setFlag("canSee", visible);
			return;
		}
		
		if (user.getRole().equals("Administrator")) {
			visible = true;
		}
		
		if (user.getRole().equals("Moderator")) {
			visible = true;
		}
		
		if (user.getRole().equals("User")) {
			if (this.type.equals("Public")) {
				visible = true;
			} else if (this.type.equals("Open")) {
				visible = true;
			}
		}
		
		setFlag("canSee", visible);
	}
	
	public void physicalDelete(User user) {
		Boolean physical = false;
		
		if (user == null) {
			setFlag("physicalDelete", physical);
			return;
		}
		
		if (user.getRole().equals("Administrator")) {
			if (ThreadDAO.getForumThreads(user, this).isEmpty() && ForumDAO.getSubforums(this, user).isEmpty()) {
				physical = true;
			}
		}
		
		setFlag("physicalDelete", physical);
	}
	
	public void setFlags(User user) {
		canSee(user);
		canEdit(user);
		canDelete(user);
		canAddSubForum(user);
		canAddThread(user);
		canLock(user);
		physicalDelete(user);
	}
}
