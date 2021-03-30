package cz.taskmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cz.taskmanager.entities.TMProcess;
import cz.taskmanager.enums.Priority;

public interface ProcessRepository extends JpaRepository<TMProcess, Long>{

	@Query("SELECT count(p) from TMProcess p where p.running = true")
	public int countRunning();

	public Optional<TMProcess> findFirstByRunningOrderByCreatedAsc(boolean running);
	
	public Optional<TMProcess> findFirstByRunningAndPriorityLessThanOrderByCreatedAsc(boolean running, Priority priority);

}
