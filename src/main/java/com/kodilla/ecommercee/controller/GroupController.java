package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.service.DbService;
import com.kodilla.ecommercee.service.GroupMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private final DbService dbService;
    private final GroupMapper groupMapper;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        List<Group> groups = dbService.getAllGroups();
        List<GroupDto> groupDtos = groups.stream()
                .map(groupMapper::mapToGroupDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groupDtos);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupId) throws GroupNotFoundException {
        Group group = dbService.findGroup(groupId);
        GroupDto groupDto = groupMapper.mapToGroupDto(group);
        return ResponseEntity.ok(groupDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto request) {
        Group group = groupMapper.mapToGroup(request);
        Group savedGroup = dbService.saveGroup(group);
        return ResponseEntity.ok(groupMapper.mapToGroupDto(savedGroup));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroupName(@PathVariable Long groupId, @RequestBody String newName) throws GroupNotFoundException {
        Group group = dbService.findGroup(groupId);
        group.setName(newName);
        Group savedGroup = dbService.saveGroup(group);
        return ResponseEntity.ok(groupMapper.mapToGroupDto(savedGroup));
    }
}