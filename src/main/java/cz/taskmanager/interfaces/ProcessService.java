package cz.taskmanager.interfaces;

import java.util.List;

import cz.taskmanager.dtos.TMProcessDto;
import cz.taskmanager.enums.Priority;

public interface ProcessService {

	void addUntilMaxCapacity(final TMProcessDto process);

	void addFIFO(TMProcessDto processDto);

	void addByPriority(TMProcessDto processDto);

	List<TMProcessDto> getAllSortedByPrio();

	List<TMProcessDto> getAllSortedById();

	List<TMProcessDto> getAllSortedByCreationTime();

	void kill(long pid);

	void killAll();

	void killWithPriority(Priority priority);
}
