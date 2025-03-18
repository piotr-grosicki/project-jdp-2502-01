package com.kodilla.ecommercee.repository;


import com.kodilla.ecommercee.domain.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

    @Override
    List<Group> findAll();

    @Override
    Optional<Group> findById(Long id);
}