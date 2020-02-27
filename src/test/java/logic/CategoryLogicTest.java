/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.TomcatStartUp;

import java.util.Map;
import entity.Category;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Min Li
 */
public class CategoryLogicTest {
    private CategoryLogic logic;
    private Map<String, String[]>sampleMap;
   
    @BeforeAll
    final static void setUpBeforeClass() throws Exception {
        TomcatStartUp.createTomcat();
        
    }
    
     @AfterAll
    final static void tearDownAfterClass() throws Exception {
      TomcatStartUp.stopAndDestroyTomcat();
    }
     
    @BeforeEach
    final void setUp() throws Exception {
        logic = new CategoryLogic();
        /*HashMap implements interface Map. Map is generic, it has two parameters, first is the Key (in our case String) and second is Value (in our case String[])*/
        sampleMap = new HashMap<>();
        /*Map stores date based on the idea of dictionaries. Each value is associated with a key. Key can be used to get a value very quickly*/
        sampleMap.put(CategoryLogic.TITLE, new String[]{"Junit 5 Test"});
        /*Map::put is used to store a key and a value inside of a map and Map::get is used to retrieve a value using a key.*/
        sampleMap.put(CategoryLogic.URL, new String[]{"junit"});
        /*In this case we are using static values stored in CategoryLogic which represent general names for Category Columns in DB to store values in Map*/
    }
    
    
    
     @Test
    final void testGetAll() {
        //get all the category from the DB
        List<Category> list = logic.getAll();
        //store the size of list/ this way we know how many categories exits in DB
        int originalSize = list.size();

        //create a new category and save it so we can delete later
        Category testCategory = logic.createEntity(sampleMap);
        //add the newly created category to DB
        logic.add(testCategory);

        //get all the category again
        list = logic.getAll();
        //the new size of category must be 1 larger than original size
        assertEquals(originalSize + 1, list.size());

        //delete the new category, so DB is reverted back to it original form
        logic.delete(testCategory);

        //get all categories again
        list = logic.getAll();
        //the new size of categories must be same as original size
        assertEquals(originalSize, list.size());
    }

     @Test
    final void testGetWithId() {
        //get all categories
        List<Category> list = logic.getAll();

        //use the first category in the list as test Category
        Category testCategory = list.get(0);
        //using the id of test category get another category from logic
       Category returnedCategory = logic.getWithId(testCategory.getId());

        //the two categories (testCategories and returnedCategories) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testCategory.getId(), returnedCategory.getId());
        assertEquals(testCategory.getUrl(), returnedCategory.getUrl());
        assertEquals(testCategory.getTitle(), returnedCategory.getTitle());
    }
    
    @Test
    final void testGetWithUrl() {
        List<Category> list = logic.getAll();
         Category testCategory = list.get(0);
        Category returnedCategory = logic.getWithUrl(testCategory.getUrl());
        assertEquals(testCategory.getId(), returnedCategory.getId());
        assertEquals(testCategory.getTitle(), returnedCategory.getTitle());
    }

    @Test
    final void testGetWithTitle() {
        List<Category> list = logic.getAll();
         Category testCategory = list.get(0);
        Category returnedCategory = logic.getWithTitle(testCategory.getTitle());
        assertEquals(testCategory.getId(), returnedCategory.getId());
        assertEquals(testCategory.getUrl(), returnedCategory.getUrl());
    }
    
    @Test
    final void testSearch() {
        List<Category> categories = logic.getAll();
        Category testCa = categories.get(0);
        String search = testCa.getTitle();
        List<Category> returned = logic.search(search);
        returned.forEach((category) -> {
            Assertions.assertTrue(category.getTitle().contains(search) || category.getUrl().contains(search));
        });
    }
    
    @Test
    final void testCreateEntity() {
        Category testCategory = new Category();
        testCategory.setTitle("Junit 5 Test");
        testCategory.setUrl("junit");
        Category testCategory2 =logic.createEntity(sampleMap);
        assertEquals(testCategory.getTitle(), testCategory2.getTitle());
        assertEquals(testCategory.getUrl(), testCategory2.getUrl());
        logic.delete(testCategory);
    }
    
      @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        List<?> hardCodedList = Arrays.asList("ID", "URL", "TITLE");
        assertIterableEquals(list, hardCodedList);
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        List<?> hardCodedList = Arrays.asList(CategoryLogic.ID, CategoryLogic.URL, CategoryLogic.TITLE);
        assertIterableEquals(list, hardCodedList);
    }

    @Test
    final void testExtraDataAsList() {     
        List<Category>category = logic.getAll();
        Category testCa = category.get(0);
        Category returnedCa = logic.getWithId(testCa.getId());
        List<?> testCaData = logic.extractDataAsList(testCa);
        List<?> returnedCaData = logic.extractDataAsList(returnedCa);
        assertIterableEquals(testCaData, returnedCaData);
    } 
}
