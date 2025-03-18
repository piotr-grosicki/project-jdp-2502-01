package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        List<GroupDto> groups = List.of(
                new GroupDto(1L, "Electronics", List.of()),
                new GroupDto(2L, "Books",List.of(1L, 2L)),
                new GroupDto(3L, "Clothing",List.of())
        );
        return ResponseEntity.ok(groups);
    }

    @PostMapping
    public ResponseEntity<GroupDto> addGroup(@RequestBody GroupDto groupDto) {
        GroupDto createdGroup = new GroupDto(4L, "Alkohol",List.of());
        return ResponseEntity.ok(createdGroup);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupId)  {
        GroupDto group = new GroupDto(groupId, "Example Group " + groupId, List.of());
        return ResponseEntity.ok(group);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroupName(@PathVariable Long groupId, @RequestBody String newName)  {
        GroupDto updatedGroup = new GroupDto(groupId, newName, List.of());
        return ResponseEntity.ok(updatedGroup);
    }
}
