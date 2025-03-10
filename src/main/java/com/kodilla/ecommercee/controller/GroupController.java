package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.service.GroupDtoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupDtoService groupDtoService;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        return ResponseEntity.ok(groupDtoService.getAllGroups());
    }

    @PostMapping
    public ResponseEntity<GroupDto> addGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(groupDtoService.addGroup(groupDto));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupId) throws GroupNotFoundException {
        return ResponseEntity.ok(groupDtoService.getGroupById(groupId));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroupName(@PathVariable Long groupId, @RequestBody String newName) throws GroupNotFoundException {
        return ResponseEntity.ok(groupDtoService.updateGroupName(groupId, newName));
    }
}
