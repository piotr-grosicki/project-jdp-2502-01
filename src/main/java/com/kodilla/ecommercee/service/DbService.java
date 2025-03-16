package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.GroupNotFoundException;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbService {
    private final GroupRepository groupRepository;
    private final ProductRepository productRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group saveGroup(final Group group) {
        return groupRepository.save(group);
    }

    public Group findGroup(final Long groupId) throws GroupNotFoundException{
        return groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
    }

    public Group updateGroup(final Long groupId, String newName) throws GroupNotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
        group.setName(newName);
        return groupRepository.save(group);
    }
}

