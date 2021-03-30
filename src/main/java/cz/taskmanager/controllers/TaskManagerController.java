package cz.taskmanager.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.taskmanager.dtos.TMProcessDto;
import cz.taskmanager.enums.Priority;
import cz.taskmanager.interfaces.ProcessService;


@RestController("processes")
public class TaskManagerController {

	private final ProcessService processService;

	public TaskManagerController(final ProcessService processService) {
		this.processService = processService;
	}

	@PostMapping
	public void saveProcess(@RequestBody TMProcessDto tmProcessDto) {
		processService.addUntilMaxCapacity(tmProcessDto);
	}

	@PostMapping("fifo")
	public void saveProcessFIFO(@RequestBody TMProcessDto tmProcessDto) {
		processService.addFIFO(tmProcessDto);
	}

	@PostMapping("prio")
	public void saveProcessByPriority(@RequestBody TMProcessDto tmProcessDto) {
		processService.addByPriority(tmProcessDto);
	}

	@GetMapping("bypriority")
	public List<TMProcessDto> getProcessesPrio() {
		return processService.getAllPrio();
	}

	@GetMapping("byid")
	public List<TMProcessDto> getProcessesById() {
		return processService.getAllById();
	}

	@GetMapping("bytime")
	public List<TMProcessDto> getProcessesByCreationTime() {
		return processService.getAllByCreationTime();
	}

	@PostMapping("kill/id")
	public void killProcess(@RequestParam long pid) {
		processService.kill(pid);
	}

	@PostMapping("killall")
	public void killAll() {
		processService.killAll();
	}

	@PostMapping("kill/priority")
	public void killProcessByPriority(@RequestParam Priority priority) {
		processService.killByPriority(priority);
	}

}
