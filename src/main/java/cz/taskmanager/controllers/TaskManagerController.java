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
import io.swagger.annotations.ApiOperation;


@RestController("processes")
public class TaskManagerController {

	private final ProcessService processService;

	public TaskManagerController(final ProcessService processService) {
		this.processService = processService;
	}

	@PostMapping
	@ApiOperation("Save process until max capacity reached")
	public void saveProcess(@RequestBody TMProcessDto tmProcessDto) {
		processService.addUntilMaxCapacity(tmProcessDto);
	}

	@PostMapping("fifo")
	@ApiOperation("Save process, remove oldest when max capacity reached")
	public void saveProcessFIFO(@RequestBody TMProcessDto tmProcessDto) {
		processService.addFIFO(tmProcessDto);
	}

	@PostMapping("prio")
	@ApiOperation("Save process, removed lowest priority oldest process when max capacity reached")
	public void saveProcessByPriority(@RequestBody TMProcessDto tmProcessDto) {
		processService.addByPriority(tmProcessDto);
	}

	@GetMapping("bypriority")
	@ApiOperation("List processes sorted by priority")
	public List<TMProcessDto> getProcessesSortedByPrio() {
		return processService.getAllSortedByPrio();
	}

	@GetMapping("byid")
	@ApiOperation("List processes sorted by pid")
	public List<TMProcessDto> getProcessesSortedByPid() {
		return processService.getAllSortedById();
	}

	@GetMapping("bytime")
	@ApiOperation("List processes sorted by creation time")
	public List<TMProcessDto> getProcessesSortedByCreationTime() {
		return processService.getAllSortedByCreationTime();
	}

	@PostMapping("kill/id")
	@ApiOperation("Kill process by pid")
	public void killProcess(@RequestParam long pid) {
		processService.kill(pid);
	}

	@PostMapping("killall")
	@ApiOperation("Kill all processes")
	public void killAll() {
		processService.killAll();
	}

	@PostMapping("kill/priority")
	@ApiOperation("Kill processes with priority")
	public void killProcessWithPriority(@RequestParam Priority priority) {
		processService.killWithPriority(priority);
	}

}
