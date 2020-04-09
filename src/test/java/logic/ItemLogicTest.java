/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.TomcatStartUp;
import entity.Item;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
public class ItemLogicTest {

    private ItemLogic logic;
    private Map<String, String[]> sampleMap;

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
        logic = new ItemLogic();
        /*HashMap implements interface Map. Map is generic, it has two parameters, first is the Key (in our case String) and second is Value (in our case String[])*/
        sampleMap = new HashMap<>();
        /*Map stores date based on the idea of dictionaries. Each value is associated with a key. Key can be used to get a value very quickly*/
        sampleMap.put(ItemLogic.PRICE, new String[]{"5"});
        /*Map::put is used to store a key and a value inside of a map and Map::get is used to retrieve a value using a key.*/
        sampleMap.put(ItemLogic.TITLE, new String[]{"junit"});
        /*In this case we are using static values stored in ItemLogic which represent general names for Item Columns in DB to store values in Map*/
        sampleMap.put(ItemLogic.DATE, new String[]{"02/02/2020"});
        sampleMap.put(ItemLogic.URL, new String[]{"www.junit5.com"});
        sampleMap.put(ItemLogic.LOCATION, new String[]{"Ottawa"});
        sampleMap.put(ItemLogic.DESCRIPTION, new String[]{"large, square"});
        sampleMap.put(ItemLogic.CATEGORY_ID, new String[]{"3"});
        sampleMap.put(ItemLogic.IMAGE_ID, new String[]{"3"});
        sampleMap.put(ItemLogic.ID, new String[]{"4"});
        /*This item has Price: " 5", Title: "junit", Date: "2020,02,02", Location:"Ottawa", Description:"large, square", Category:"furniture"*/
    }

    @AfterEach
    final void tearDown() throws Exception {
    }

    @Test
    final void testGetAll() {
        //get all the items from the DB
        List<Item> list = logic.getAll();
        //store the size of list/ this way we know how many items exits in DB
        int originalSize = list.size();

        //create a new Item and save it so we can delete later
        Item testItem = logic.createEntity(sampleMap);
        testItem.setCategory(new CategoryLogic().getWithId(1));
        testItem.setImage(new ImageLogic().getWithId(1));
        //add the newly created item to DB
        logic.add(testItem);

        //get all the items again
        list = logic.getAll();
        //the new size of items must be 1 larger than original size
        assertEquals(originalSize + 1, list.size());

        //delete the new item, so DB is reverted back to it original form
        logic.delete(testItem);

        //get all items again
        list = logic.getAll();
        //the new size of items must be same as original size
        assertEquals(originalSize, list.size());
    }

    @Test
    final void testGetWithId() {
        //get all items
        List<Item> list = logic.getAll();
        //use the first item in the list as test item
        Item testItem = list.get(0);
        //using the id of test item get another item from logic
        Item returnedItem = logic.getWithId(testItem.getId());

        //the two items (testItem and returnedItem) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testItem.getId(), returnedItem.getId());
        assertEquals(testItem.getUrl(), returnedItem.getUrl());
        assertEquals(testItem.getPrice(), returnedItem.getPrice());
        assertEquals(testItem.getTitle(), returnedItem.getTitle());
        assertEquals(testItem.getLocation(), returnedItem.getLocation());
        assertEquals(testItem.getDescription(), returnedItem.getDescription());
    }

    @Test
    final void testGetWithPrice() {
        List<Item> list = logic.getAll();
        Item testItem = list.get(0);
        List<Item> returnedItems = logic.getWithPrice(testItem.getPrice());
        for (Item item : returnedItems) {
            assertEquals(testItem.getId(), item.getId());
            assertEquals(testItem.getUrl(), item.getUrl());
            assertEquals(testItem.getPrice(), item.getPrice());
            assertEquals(testItem.getTitle(), item.getTitle());
            assertEquals(testItem.getLocation(), item.getLocation());
            assertEquals(testItem.getDescription(), item.getDescription());
        }
    }

    @Test
    final void testGetWithTitle() {
        List<Item> list = logic.getAll();
        Item testItem = list.get(0);
        List<Item> returnedItems = logic.getWithTitle(testItem.getTitle());
        for (Item item : returnedItems) {
            assertEquals(testItem.getId(), item.getId());
            assertEquals(testItem.getUrl(), item.getUrl());
            assertEquals(testItem.getPrice(), item.getPrice());
            assertEquals(testItem.getTitle(), item.getTitle());
            assertEquals(testItem.getLocation(), item.getLocation());
            assertEquals(testItem.getDescription(), item.getDescription());
        }
    }
 
    @Test
    final void testGetWithLocation() {
        List<Item> list = logic.getAll();
        Item testItem = list.get(0);
        List<Item> returnedItems = logic.getWithLocation(testItem.getLocation());
        for (Item item : returnedItems) {
            assertEquals(testItem.getId(), item.getId());
            assertEquals(testItem.getUrl(), item.getUrl());
            assertEquals(testItem.getPrice(), item.getPrice());
            assertEquals(testItem.getTitle(), item.getTitle());
            assertEquals(testItem.getLocation(), item.getLocation());
            assertEquals(testItem.getDescription(), item.getDescription());
        }

    }

    @Test
    final void testGetWithDescription() {
        List<Item> list = logic.getAll();
        Item testItem = list.get(0);
        List<Item> returnedItems = logic.getWithDescription(testItem.getDescription());
        for (Item item : returnedItems) {
            assertEquals(testItem.getId(), item.getId());
            assertEquals(testItem.getUrl(), item.getUrl());
            assertEquals(testItem.getPrice(), item.getPrice());
            assertEquals(testItem.getTitle(), item.getTitle());
            assertEquals(testItem.getLocation(), item.getLocation());
            assertEquals(testItem.getDescription(), item.getDescription());
        }
    }

    @Test
    final void testGetWithUrl() {
        List<Item> list = logic.getAll();
        Item testItem = list.get(0);
        Item returnedItem = logic.getWithUrl(testItem.getUrl());
        assertEquals(testItem.getId(), returnedItem.getId());
        assertEquals(testItem.getUrl(), returnedItem.getUrl());
        assertEquals(testItem.getPrice(), returnedItem.getPrice());
        assertEquals(testItem.getTitle(), returnedItem.getTitle());
        assertEquals(testItem.getLocation(), returnedItem.getLocation());
        assertEquals(testItem.getDescription(), returnedItem.getDescription());
    }

    @Test
    final void testGetWithCategory() {
        List<Item> list = logic.getAll();
        Item testItem = list.get(0);
        List<Item> returnedItems = logic.getWithCategory(testItem.getCategory().getId());
        for(Item items: returnedItems){
            assertEquals(testItem.getCategory(), items.getCategory());
        }
    }

    @Test
    final void testCreateEntity() {
        Item testItem = logic.createEntity(sampleMap);
        testItem.setCategory(new CategoryLogic().getWithId(1));
        testItem.setImage(new ImageLogic().getWithId(1));
        logic.add(testItem);
        Item item = logic.getWithId(testItem.getId());
        assertEquals(testItem.getId(), item.getId());
        assertEquals(testItem.getUrl(), item.getUrl());
        assertEquals(testItem.getPrice().compareTo( item.getPrice()),0);
        assertEquals(testItem.getTitle(), item.getTitle());
        assertEquals(testItem.getLocation(), item.getLocation());
        assertEquals(testItem.getDescription(), item.getDescription());
        assertEquals(testItem.getCategory(), item.getCategory());
        assertEquals(testItem.getImage(), item.getImage());
        logic.delete(testItem);
    }

    @Test
    final void testSearch() {
        List<Item> list = logic.getAll();
        Item testItem = list.get(0);
        String search = testItem.getTitle();
        List<Item> returned = logic.search(search);
        returned.forEach((item) -> {
            Assertions.assertTrue(item.getUrl().contains(search) ||
                     item.getTitle().contains(search) || item.getLocation().contains(search) || item.getDescription().contains(search));
        });
    }

    @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        List<?> hardCodedList = Arrays.asList("ID", "URL", "DATE", "TITLE", "PRICE", "LOCATION", "IMAGE_ID", "CATEGORY_ID", "DESCRIPTION");
        assertIterableEquals(list, hardCodedList);
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        List<?> hardCodedList = Arrays.asList(ItemLogic.ID, ItemLogic.URL, ItemLogic.DATE, ItemLogic.TITLE, ItemLogic.PRICE, ItemLogic.LOCATION, ItemLogic.IMAGE_ID,
                ItemLogic.CATEGORY_ID, ItemLogic.DESCRIPTION);
        assertIterableEquals(list, hardCodedList);
    }

    @Test
    final void testExtraDataAsList() {
        List<Item> items = logic.getAll();
        Item testIt = items.get(0);
        Item returnedIt = logic.getWithId(testIt.getId());
        List<?> testItData = logic.extractDataAsList(testIt);
        List<?> returnedItData = logic.extractDataAsList(returnedIt);
        assertIterableEquals(testItData, returnedItData);
    }
}
