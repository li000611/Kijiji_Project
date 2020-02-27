/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.TomcatStartUp;
import entity.Image;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Min Li
 */
public class ImageLogicTest {
    private static Tomcat tomcat;
    private ImageLogic logic;  
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
        logic = new ImageLogic();
        /*HashMap implements interface Map. Map is generic, it has three parameters, first is the Key (in our case String) and second is Value (in our case String[])*/
        sampleMap = new HashMap<>();
        /*Map stores date based on the idea of dictionaries. Each value is associated with a key. Key can be used to get a value very quickly*/
        sampleMap.put(ImageLogic.NAME, new String[]{"Junit 5 Test"});
        /*Map::put is used to store a key and a value inside of a map and Map::get is used to retrieve a value using a key.*/
        sampleMap.put(ImageLogic.PATH, new String[]{"junit"});
        /*In this case we are using static values stored in AccoundLogic which represent general names for Image Columns in DB to store values in Map*/
        sampleMap.put(ImageLogic.URL, new String[]{"https://junit5.com"});
        /*This image has Name: "Junit 5 Test", Path: "junit", and Url: "https://junit5.com"*/   
    }

    @AfterEach
    final void tearDown() throws Exception {
    }
    
    @Test
    final void testGetAll() {
        //get all the image from the DB
        List<Image> list = logic.getAll();
        //store the size of list/ this way we know how many images exits in DB
        int originalSize = list.size();

        //create a new image and save it so we can delete later
        Image testImage = logic.createEntity(sampleMap);
        //add the newly created image to DB
        logic.add(testImage);

        //get all the image again
        list = logic.getAll();
        //the new size of image must be 1 larger than original size
        assertEquals(originalSize + 1, list.size());

        //delete the new image, so DB is reverted back to it original form
        logic.delete(testImage);

        //get all image again
        list = logic.getAll();
        //the new size of images must be same as original size
        assertEquals(originalSize, list.size());
    }

     @Test
    final void testGetWithId() {
        //get all images
        List<Image> list = logic.getAll();
        //use the first image in the list as test iamge
        Image testImage = list.get(0);
        //using the id of test image get another image from logic
       Image returnedImages = logic.getWithId(testImage.getId());

        //the two images (testImage and returnedImage) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testImage.getId(), returnedImages.getId());
        assertEquals(testImage.getUrl(), returnedImages.getUrl());
        assertEquals(testImage.getName(), returnedImages.getName());
        assertEquals(testImage.getPath(), returnedImages.getPath());
        
    }
    
    @Test
    final void testGetWithUrl() {
        List<Image> list = logic.getAll();
         Image testImage = list.get(0);
        List<Image> returnedImages = logic.getWithUrl(testImage.getUrl());
        for(Image image : returnedImages){
        assertEquals(image.getId(), testImage.getId());
         assertEquals(image.getUrl(), testImage.getUrl());
        assertEquals(image.getName(), testImage.getName());
         assertEquals(image.getPath(), testImage.getPath()); 
        }
    }

    @Test
    final void testGetWithName() {
        List<Image> list = logic.getAll();
         Image testImage = list.get(0);
        List<Image> returnedImages = logic.getWithName(testImage.getName());
        for(Image image : returnedImages){
        assertEquals(image.getId(), testImage.getId());
         assertEquals(image.getUrl(), testImage.getUrl());
        assertEquals(image.getName(), testImage.getName());
         assertEquals(image.getPath(), testImage.getPath()); 
        }
    }
    
    @Test
    final void testGetWithPath() {
        List<Image> list = logic.getAll();
         Image testImage = list.get(0);
        Image returnedImages = logic.getWithPath(testImage.getPath());
        assertEquals(testImage.getId(), returnedImages.getId());
         assertEquals(testImage.getUrl(), returnedImages.getUrl());
        assertEquals(testImage.getName(), returnedImages.getName());
         assertEquals(testImage.getPath(), returnedImages.getPath());
        
    }
    
    @Test
    final void testSearch() {
         List<Image> images = logic.getAll();
       Image testAc = images.get(0);
        String search = testAc.getName();
        List<Image> returned = logic.search(search);
        returned.forEach((image) -> {
            Assertions.assertTrue(image.getName().contains(search) || image.getUrl().contains(search) || image.getPath().contains(search));
        });       
    }
    
    @Test
    final void testCreateEntity() {
        Image image = new Image();
        image.setName("Junit 5 Test");
        image.setUrl("https://junit5.com");
        image.setPath("junit");
        Image testImage =logic.createEntity(sampleMap);
        assertEquals(image.getName(), testImage.getName());
        assertEquals(image.getUrl(), testImage.getUrl());
        assertEquals(image.getPath(), testImage.getPath());
        logic.delete(image);
    }
    
      @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        List<?> hardCodedList = Arrays.asList("ID", "URL", "PATH","NAME");
        assertIterableEquals(list, hardCodedList);
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        List<?> hardCodedList = Arrays.asList(ImageLogic.ID, ImageLogic.URL, ImageLogic.PATH,ImageLogic.NAME);
        assertIterableEquals(list, hardCodedList);
    }

    @Test
    final void testExtraDataAsList() {     
       List<Image>image = logic.getAll();
       Image testIm = image.get(0);
        Image returnedIm = logic.getWithId(testIm.getId());
        List<?> testImData = logic.extractDataAsList(testIm);
        List<?> returnedImData = logic.extractDataAsList(returnedIm);
        assertIterableEquals(testImData, returnedImData);
   
    } 

}