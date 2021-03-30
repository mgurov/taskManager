package cz.taskmanager.enums;

public enum Priority {
	LOW(1),
	MEDIUM(2),
	HIGH(3);
	
	private int prio;
	
	Priority(int prio) {
		this.prio = prio;
	}
	
	public int getPrio() {
		return this.prio;
	}
}
