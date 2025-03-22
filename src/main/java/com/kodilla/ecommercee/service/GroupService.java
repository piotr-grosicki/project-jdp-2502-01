package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.GroupNotFoundException;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.repository.GroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(groupMapper::mapToGroupDto)
                .collect(Collectors.toList());
    }

    public GroupDto getGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group with id " + groupId + " not found"));
        return groupMapper.mapToGroupDto(group);
    }

    public GroupDto createGroup(GroupDto request) {
        Group group = groupMapper.mapToGroup(request);
        Group savedGroup = groupRepository.save(group);
        return groupMapper.mapToGroupDto(savedGroup);
    }

    public GroupDto updateGroupName(Long groupId, GroupDto dto){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group with id " + groupId + " not found"));
        group.setName(dto.getName());
        Group savedGroup = groupRepository.save(group);
        return groupMapper.mapToGroupDto(savedGroup);
    }
}
