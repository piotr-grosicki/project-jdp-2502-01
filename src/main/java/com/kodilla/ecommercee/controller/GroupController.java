package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/v1/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupId) throws GroupNotFoundException {
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroupName(@PathVariable Long groupId, @RequestBody GroupDto dto) throws GroupNotFoundException {
        return ResponseEntity.ok(groupService.updateGroupName(groupId, dto));
    }
}
