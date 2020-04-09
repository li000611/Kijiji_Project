/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.ValidationException;
import dal.ItemDAL;
import entity.Item;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 *
 * @author Min Li
 */
public class ItemLogic extends GenericLogic<Item, ItemDAL> {

    public static final String DESCRIPTION = "description";
    public static final String CATEGORY_ID = "categoryId";
    public static final String IMAGE_ID = "imageId";
    public static final String LOCATION = "location";
    public static final String PRICE = "price";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String URL = "url";
    public static final String ID = "id";

    public ItemLogic() {
        super(new ItemDAL());
    }

    @Override
    public List<Item> getAll() {
        return get(() -> dao().findAll());
    }

    @Override
    public Item getWithId(int id) {
        return get(() -> dao().findById(id));
    }

    public List<Item> getWithPrice(BigDecimal price) {
        return get(() -> dao().findByPrice(price));
    }

    public List<Item> getWithTitle(String title) {
        return get(() -> dao().findByTitle(title));
    }

    public List<Item> getWithDate(String date) {
        return get(() -> dao().findByDate(date));
    }

    public List<Item> getWithLocation(String location) {
        return get(() -> dao().findByLocation(location));
    }

    public List<Item> getWithDescription(String description) {
        return get(() -> dao().findByDescription(description));
    }

    public Item getWithUrl(String url) {
        return get(() -> dao().findByUrl(url));
    }

    public List<Item> getWithCategory(int category) {
        return get(() -> dao().findByCategory(category));
    }

    public List<Item> search(String search) {
        return get(() -> dao().findContaining(search));
    }

    public Item createEntity(Map<String, String[]> parameterMap) {
        Item item = new Item();
        if (parameterMap.containsKey(ID)) {
            item.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        
        String s = parameterMap.get(PRICE)[0];
        s = s.replace("$", "");
        s = s.replace(",", "");
        try {
            item.setPrice(new BigDecimal(s));
        } catch (NumberFormatException ex) {
        }
        
         if(parameterMap.containsKey(TITLE)){
                       if(parameterMap.get(TITLE)[0].isEmpty() || parameterMap.get(TITLE)[0].length() > 255){
                          throw new ValidationException("Invalid Title");
                       }else{
                         item.setTitle(parameterMap.get(TITLE)[0]);
                       }
         }
        
        try {
            item.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(parameterMap.get(DATE)[0]));
        } catch (ParseException ex) {
            item.setDate(new Date());
        }
        
         if(parameterMap.containsKey(LOCATION)){
                       if(parameterMap.get(LOCATION)[0].isEmpty() || parameterMap.get(LOCATION)[0].length() > 45){
                          throw new ValidationException("Invalid Location");
                       }else{
                        item.setLocation(parameterMap.get(LOCATION)[0]);
                       }
         }
         
         /*
         if(parameterMap.containsKey(DESCRIPTION)){
                       if(parameterMap.get(DESCRIPTION)[0].isEmpty() || parameterMap.get(DESCRIPTION)[0].length() > 255){
                          throw new ValidationException("Invalid Description");
                       }else{
                        item.setDescription(parameterMap.get(DESCRIPTION)[0]);
                       }
         }
        */
         item.setDescription(parameterMap.get(DESCRIPTION)[0]);
         
          if(parameterMap.containsKey(URL)){
                       if(parameterMap.get(URL)[0].isEmpty() || parameterMap.get(URL)[0].length() > 255){
                          throw new ValidationException("Invalid Url");
                       }else{
                        item.setUrl(parameterMap.get(URL)[0]);
                       }
         }else{
              item.setUrl("dont have url");
          }
          
        return item;
    }

    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "URL", "DATE", "TITLE", "PRICE", "LOCATION", "IMAGE_ID", "CATEGORY_ID", "DESCRIPTION");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList("id", "url", "date", "title", "price", "location", "imageId", "categoryId", "description");
    }

    @Override
    public List<?> extractDataAsList(Item e) {
        return Arrays.asList(e.getId(), e.getUrl(), e.getDate(), e.getTitle(), e.getPrice(), e.getLocation(), e.getImage().getId(), e.getCategory().getId(), e.getDescription());

    }
}
