package cz.taskmanager.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import cz.taskmanager.enums.Priority;

@Entity
public final class TMProcess {
	
	@Id @Column(unique=true, updatable=false)
	private long pid;
	
	@Column(updatable=false)
	private Priority priority;
	
	@Column(updatable=false)
	private LocalDateTime created;
	
	@Column
	private boolean running;
	
	public TMProcess() {
	}
	
	public TMProcess(long pid, Priority priority) {
		this.pid = pid;
		this.priority = priority;
		this.created = LocalDateTime.now();
		this.running = true;
	}

	public long getPid() {
		return pid;
	}

	public Priority getPriority() {
		return priority;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}
	
	public boolean isRunning() {
		return running;
	}

	public void kill() {
		this.running = false;
	}

}
