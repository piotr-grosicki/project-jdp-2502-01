package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import jakarta.transaction.Transactional;
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
        Group group = new Group(null, "Electronics", new ArrayList<>());

        //When
        Group savedGroup = groupRepository.save(group);

        //Then
        Assertions.assertNotNull(savedGroup.getId());
        Assertions.assertEquals("Electronics", savedGroup.getName());
    }

    @Test
    public void findGroupByIdTest() {
        //Given
        Group group = new Group(null, "Electronics", new ArrayList<>());
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
        Group group1 = new Group(null , "Electronics", new ArrayList<>());
        Group group2 = new Group(null, "Clothing", new ArrayList<>());
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
        Group group1 = new Group(null , "Electronics", new ArrayList<>());
        Group group2 = new Group(null, "Clothing", new ArrayList<>());
        groupRepository.save(group1);
        groupRepository.save(group2);

        //When
        groupRepository.delete(group2);
        List<Group> groups = (List<Group>) groupRepository.findAll();

        //Then
        Assertions.assertEquals(1, groups.size());
    }

    @Test
    public void updateGroupTest() {
        //Given
        Group group = new Group(null , "Electronics", new ArrayList<>());
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
    @Transactional
    public void findProductListAssignedToGroupTest() {
        //Given
        Group group = new Group(1L , "Electronics", new ArrayList<>());
        groupRepository.save(group);

        Product product1 = new Product(1L, "LED TV", "Very big LED TV", new BigDecimal("6999"), group);
        Product product2 = new Product(2L, "Soundbar", "Very laud soundbar", new BigDecimal("3999"), group);
        productRepository.save(product1);
        productRepository.save(product2);

        group.getProducts().add(product1);
        group.getProducts().add(product2);

        groupRepository.save(group);

        System.out.println(groupRepository.findByIdWithProducts(group.getId()));

        // Sprawdzam czy produkty zostały dodane do bazy
            List<Product> allProducts = (List<Product>) productRepository.findAll();
            System.out.println("Products in database: " + allProducts.size());

        //When
        Optional<Group> foundGroup = groupRepository.findByIdWithProducts(group.getId());

            // Sprawdzam czy produkty zostały przypisane do grupy
            Group retrievedGroup = foundGroup.get();
            System.out.println("Group ID: " + retrievedGroup.getId());
            System.out.println("Products in group: " + retrievedGroup.getProducts());
            System.out.println("Retrieved Products: " + retrievedGroup.getProducts().size());
            retrievedGroup.getProducts().forEach(p -> System.out.println("Product: " + p.getName()));

        //Then
        Assertions.assertTrue(foundGroup.isPresent());
        Assertions.assertNotNull(retrievedGroup.getProducts());
        Assertions.assertFalse(retrievedGroup.getProducts().isEmpty());
        Assertions.assertEquals(2, retrievedGroup.getProducts().size());

    }

//    @Test
//    @Transactional
//    public void deleteGroupWithProductTest() {
//        //Given
//        Group group = new Group(1L , "Electronics", new ArrayList<>());
//        Group savedGroup = groupRepository.save(group);
//
//        Product product1 = new Product(1L, "LED TV", "Very big LED TV", new BigDecimal("6999"), group);
//        Product product2 = new Product(2L, "Soundbar", "Very laud soundbar", new BigDecimal("3999"), group);
//        productRepository.save(product1);
//        productRepository.save(product2);
//        System.out.println(groupRepository.findByIdWithProducts(group.getId()));
//
//            // Sprawdzam czy produkty zostały przypisane do grupy
//            Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());
//            Group retrievedGroup = foundGroup.get();
//            System.out.println("Group ID: " + retrievedGroup.getId());
//            System.out.println("Products in group: " + retrievedGroup.getProducts());
//            System.out.println("Retrieved Products: " + retrievedGroup.getProducts().size());
//            retrievedGroup.getProducts().forEach(p -> System.out.println("Product: " + p.getName()));
//
//        //When & Then
//        Exception exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {groupRepository.delete(savedGroup);});
//        System.out.println(exception.getMessage());
//        Assertions.assertTrue(foundGroup.isPresent());
//    }


    @Test
    @Transactional
    public void deleteGroupWithProductTest() {
        //Given
        Group group = new Group(null , "Electronics", new ArrayList<>());
        groupRepository.save(group);

        Product product1 = new Product(null, "LED TV", "Very big LED TV", new BigDecimal("6999"), group);
        Product product2 = new Product(null, "Soundbar", "Very laud soundbar", new BigDecimal("3999"), group);
        productRepository.save(product1);
        productRepository.save(product2);

        group.getProducts().add(product1);
        group.getProducts().add(product2);
        groupRepository.save(group);

        //When
        groupRepository.deleteById(group.getId());

        //Then
        Assertions.assertFalse(groupRepository.findById(group.getId()).isPresent());
        Assertions.assertEquals(0, productRepository.findAll().size());
    }
}
