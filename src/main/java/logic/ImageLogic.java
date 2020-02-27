/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.ValidationException;
import dal.ImageDAL;
import entity.Image;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Min Li
 */
public class ImageLogic extends GenericLogic<Image, ImageDAL>{
   
       public static final String PATH = "path";
       public static final String NAME = "name";
       public static final String URL = "url";
       public static final String ID = "id";
    
    public ImageLogic() {
        super(new ImageDAL());
    }

     @Override
    public List<Image> getAll() {
         return get(()->dao().findAll());
    }
    
    @Override
    public Image getWithId(int id) {
        return get(()->dao().findById(id));
    }
    
      public List<Image> getWithUrl(String url){
          return get(()->dao().findByUrl(url)); 
    } 
    
      public Image getWithPath(String path) {
        return get(()->dao().findByPath(path));
    }
         
      public List <Image> getWithName(String name){
        return get(()->dao().findByName(name));
      }
              
       public List<Image> search(String search){
         return get(()->dao().findContaining(search));
    }    
       
        @Override
    public Image createEntity(Map<String, String[]> parameterMap) {
       Image image = new Image();
         if(parameterMap.containsKey(ID)){
            image.setId(Integer.parseInt(parameterMap.get(ID)[0]));
          }
         
       /*
         if(parameterMap.containsKey(NAME)){
                       if(parameterMap.get(NAME)[0].isEmpty() || parameterMap.get(NAME)[0].length() > 255){
                          throw new ValidationException("Invalid Name");
                       }else{
                       image.setName(parameterMap.get(NAME)[0]);
                       }
           }
         */
         image.setName(parameterMap.get(NAME)[0]);
         
         if(parameterMap.containsKey(PATH)){
                       if(parameterMap.get(PATH)[0].isEmpty() || parameterMap.get(PATH)[0].length() > 255){
                          throw new ValidationException("Invalid Path");
                       }else{
                       image.setPath(parameterMap.get(PATH)[0]);                      
                       }
            }
    
         /*
         if(parameterMap.containsKey(URL)){
                       if(parameterMap.get(URL)[0].isEmpty() || parameterMap.get(URL)[0].length() > 255){
                          throw new ValidationException("Invalid Url");
                       }else{
                       image.setUrl(parameterMap.get(URL)[0]);                      
                       }
            } 
          */
         
         image.setUrl(parameterMap.get(URL)[0]);   
         
        return image;
       
    }
       
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "URL", "PATH", "NAME");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList("id", "url", "path", "name");
    }

    @Override
    public List<?> extractDataAsList(Image e) {
         return Arrays.asList(e.getId(), e.getUrl(), e.getPath(), e.getName());
    }
}
