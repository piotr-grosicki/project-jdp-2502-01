package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.GroupNotFoundException;
import com.kodilla.ecommercee.domain.GroupDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupDtoService {
//########################## HARD CODED DATA ############################
//#######################################################################
    private final List<GroupDto> groups = new ArrayList<>();

    public GroupDtoService() {
        groups.add(new GroupDto(1L, "Phones", new ArrayList<>()));
        groups.add(new GroupDto(2L, "Laptops", new ArrayList<>(List.of(1L, 2L,3L,4L,5L))));
        groups.add(new GroupDto(3L, "Tablets", new ArrayList<>(List.of(1L,2L))));
        groups.add(new GroupDto(4L, "Monitors", new ArrayList<>(List.of(3L,4L,5L))));
        groups.add(new GroupDto(5L, "Printers", new ArrayList<>(List.of(1L,2L,3L,4L,5L))));
    }
//#######################################################################
    public List<GroupDto> getAllGroups() {
        return groups; }

    public GroupDto addGroup(GroupDto group) {
        groups.add(group);
        return group;
    }

    public GroupDto getGroupById(Long groupId) throws GroupNotFoundException {
        return groups.stream()
                .filter(group -> group.getId().equals(groupId))
                .findFirst()
                .orElseThrow(GroupNotFoundException::new);
    }

    public GroupDto updateGroupName(Long groupId, String newName) throws GroupNotFoundException {
        return groups.stream()
                .filter(group -> group.getId().equals(groupId))
                .findFirst()
                .map(group -> {
                    group.setName(newName);
                    return group;
                })
                .orElseThrow(GroupNotFoundException::new);
    }
}