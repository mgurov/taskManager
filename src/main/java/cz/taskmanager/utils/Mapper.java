package cz.taskmanager.utils;

import java.util.List;
import java.util.stream.Collectors;

import cz.taskmanager.dtos.TMProcessDto;
import cz.taskmanager.entities.TMProcess;

public class Mapper {

	public static List<TMProcessDto> mapList(final List<TMProcess> processes) {
		return processes.stream().map(Mapper::map).collect(Collectors.toList());
	}
	
	public static TMProcessDto map(final TMProcess process) {
		return new TMProcessDto(process.getPid(), process.getPriority());
	}
	
	
	public static TMProcess mapDto(final TMProcessDto processDto) {
		return new TMProcess(processDto.getPid(), processDto.getPriority());
	}

	
}
