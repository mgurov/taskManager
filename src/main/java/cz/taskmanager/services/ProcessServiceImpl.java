package cz.taskmanager.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cz.taskmanager.config.AppConfig;
import cz.taskmanager.dtos.TMProcessDto;
import cz.taskmanager.entities.TMProcess;
import cz.taskmanager.enums.Priority;
import cz.taskmanager.interfaces.ProcessService;
import cz.taskmanager.repositories.ProcessRepository;
import cz.taskmanager.utils.Mapper;

@Component
public class ProcessServiceImpl implements ProcessService {

	private final ProcessRepository processRepository;

	private final AppConfig appConfig;

	public ProcessServiceImpl(final ProcessRepository processRepository, final AppConfig appConfig) {
		this.processRepository = processRepository;
		this.appConfig = appConfig;
	}

	@Override
	public void addUntilMaxCapacity(final TMProcessDto processDto) {
		checkExists(processDto);
		if (processRepository.countRunning() >= appConfig.getMaxCapacity()) {
			throw new IllegalStateException();
		}
		processRepository.save(Mapper.mapDto(processDto));
	}

	@Override
	public void addFIFO(final TMProcessDto processDto) {
		checkExists(processDto);
		if (processRepository.countRunning() >= appConfig.getMaxCapacity()) {
			Optional<TMProcess> oldestProcessOpt = processRepository.findFirstByRunningOrderByCreatedAsc(true);
			if (oldestProcessOpt.isPresent()) {
				TMProcess oldestProcess = oldestProcessOpt.get();
				oldestProcess.kill();
				processRepository.save(oldestProcess);
			}
		}

		processRepository.save(Mapper.mapDto(processDto));
	}

	@Override
	public void addByPriority(final TMProcessDto processDto) {
		checkExists(processDto);
		if (processRepository.countRunning() >= appConfig.getMaxCapacity()) {
			Optional<TMProcess> oldestLowerProcessOpt = processRepository
					.findFirstByRunningAndPriorityLessThanOrderByCreatedAsc(true, processDto.getPriority());
			if (oldestLowerProcessOpt.isPresent()) {
				TMProcess oldestLowerProcess = oldestLowerProcessOpt.get();
				oldestLowerProcess.kill();
				processRepository.save(oldestLowerProcess);
				processRepository.save(Mapper.mapDto(processDto));
			} else {
				throw new IllegalStateException();
			}
		} else {
			processRepository.save(Mapper.mapDto(processDto));
		}
	}

	@Override
	public List<TMProcessDto> getAllSortedByPrio() {
		List<TMProcess> processes = processRepository.findAll();
		return processes.stream().filter(process -> process.isRunning() == true)
				.sorted(Comparator.comparing(TMProcess::getPriority)).map(Mapper::map).collect(Collectors.toList());

	}

	@Override
	public List<TMProcessDto> getAllSortedById() {
		List<TMProcess> processes = processRepository.findAll();
		return processes.stream().filter(process -> process.isRunning() == true)
				.sorted(Comparator.comparing(TMProcess::getPid)).map(Mapper::map).collect(Collectors.toList());

	}

	@Override
	public List<TMProcessDto> getAllSortedByCreationTime() {
		List<TMProcess> processes = processRepository.findAll();
		return processes.stream().filter(process -> process.isRunning() == true)
				.sorted(Comparator.comparing(TMProcess::getCreated)).map(Mapper::map).collect(Collectors.toList());

	}

	@Override
	public void kill(final long pid) {
		checkKill(pid);
		Optional<TMProcess> processOpt = processRepository.findById(pid);
		if (processOpt.isPresent()) {
			TMProcess process = processOpt.get();
			process.kill();
			processRepository.save(process);
		}

	}

	@Override
	public void killAll() {
		List<TMProcess> processes = processRepository.findAll();
		List<TMProcess> runningProcesses = processes.stream().filter(p -> p.isRunning() == true)
				.collect(Collectors.toList());
		runningProcesses.forEach(p -> p.kill());

		processRepository.saveAll(runningProcesses);
	}

	@Override
	public void killWithPriority(Priority priority) {
		List<TMProcess> processes = processRepository.findAll();
		List<TMProcess> priorityProcesses = processes.stream().filter(p -> p.getPriority() == priority)
				.collect(Collectors.toList());
		priorityProcesses.forEach(p -> p.kill());

		processRepository.saveAll(priorityProcesses);
	}

	private void checkExists(final TMProcessDto processDto) {
		if (processRepository.existsById(processDto.getPid())) {
			throw new IllegalArgumentException();
		}
	}

	private void checkKill(final long pid) {
		Optional<TMProcess> process = processRepository.findById(pid);
		if (process.isEmpty() || !process.get().isRunning()) {
			throw new IllegalArgumentException();
		}
	}

}
