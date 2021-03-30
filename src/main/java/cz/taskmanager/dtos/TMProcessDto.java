package cz.taskmanager.dtos;

import java.io.Serializable;

import cz.taskmanager.enums.Priority;

public final class TMProcessDto implements Serializable {
	
	private static final long serialVersionUID = -8692108749001793477L;

	private final long pid;

	private Priority priority;
	
	public TMProcessDto(long pid, Priority priority) {
		this.pid = pid;
		this.priority = priority;
	}

	public long getPid() {
		return pid;
	}

	public Priority getPriority() {
		return priority;
	}
	
}
