package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class GroupTestSuite {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void cleanUp() {
        groupRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void addGroupTest() {
        //Given
        Group group = createGroup("Electronics");

        //When
        Group savedGroup = groupRepository.save(group);

        //Then
        Assertions.assertNotNull(savedGroup.getId());
        Assertions.assertEquals("Electronics", savedGroup.getName());
    }

    @Test
    public void findGroupByIdTest() {
        //Given
        Group group = createGroup("Electronics");
        Group savedGroup = groupRepository.save(group);

        //When
        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());

        //Then
        Assertions.assertTrue(foundGroup.isPresent());
        Assertions.assertEquals("Electronics", foundGroup.get().getName());
    }

    @Test
    public void findAllGroupsTest() {
        //Given
        Group group1 = createGroup("Electronics");
        Group group2 = createGroup("Clothing");
        groupRepository.save(group1);
        groupRepository.save(group2);

        //When
        List<Group> groups = (List<Group>) groupRepository.findAll();

        //Then
        Assertions.assertEquals(2, groups.size());
    }

    @Test
    public void deleteGroupTest() {
        //Given
        Group group1 = createGroup("Electronics");
        Group group2 = createGroup("Clothing");
        groupRepository.save(group1);
        groupRepository.save(group2);
        Long group2Id = group2.getId();

        //When
        groupRepository.delete(group2);

        //Then
        Assertions.assertFalse(groupRepository.findById(group2Id).isPresent());
    }

    @Test
    public void updateGroupTest() {
        //Given
        Group group = createGroup("Electronics");
        Group savedGroup = groupRepository.save(group);
        Long id = savedGroup.getId();

        //When
        Optional<Group> foundGroup = groupRepository.findById(id);
        foundGroup.ifPresent(g -> {
            g.setName("Clothing");
            groupRepository.save(g);
        });
        Optional<Group> updatedGroup = groupRepository.findById(id);

        //Then
        Assertions.assertTrue(updatedGroup.isPresent());
        Assertions.assertEquals("Clothing", updatedGroup.get().getName());

    }

    @Test
    public void findProductListAssignedToGroupTest() {
        //Given
        Group group = createGroup("Electronics");
        groupRepository.save(group);

        Product product1 = createProduct("LED TV", "Very big LED TV", new BigDecimal("6999"), group);
        Product product2 = createProduct("Soundbar", "Very laud soundbar", new BigDecimal("3999"), group);
        productRepository.save(product1);
        productRepository.save(product2);

        //When
        Optional<Group> foundGroup = groupRepository.findById(group.getId());
        Group retrievedGroup = foundGroup.get();

        //Then
        Assertions.assertTrue(foundGroup.isPresent());
        Assertions.assertNotNull(retrievedGroup.getProducts());
        Assertions.assertFalse(retrievedGroup.getProducts().isEmpty());
        Assertions.assertEquals(2, retrievedGroup.getProducts().size());

    }

    @Test
    public void deleteGroupWithProductTest() {
        //Given
        Group group = createGroup("Electronics");
        Group savedGroup = groupRepository.save(group);

        Product product1 = createProduct("LED TV", "Very big LED TV", new BigDecimal("6999"), group);
        Product product2 = createProduct("Soundbar", "Very laud soundbar", new BigDecimal("3999"), group);
        productRepository.save(product1);
        productRepository.save(product2);

        //When & Then
        Exception exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            groupRepository.delete(savedGroup);
        });
        Assertions.assertTrue(groupRepository.findById(group.getId()).isPresent());
        Assertions.assertTrue(productRepository.findById(product1.getId()).isPresent());
        Assertions.assertTrue(productRepository.findById(product2.getId()).isPresent());
    }

    public static Group createGroup(String name) {
        return new Group(null, name, new ArrayList<>());
    }

    public static Product createProduct(String name, String description, BigDecimal price, Group group) {
        return new Product(null, name, description, price, group);
    }
}
