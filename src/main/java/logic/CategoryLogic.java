/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import common.ValidationException;
import dal.CategoryDAL;
import entity.Category; 
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Min Li
 */
public class CategoryLogic extends GenericLogic<Category, CategoryDAL>{
      public static final String TITLE = "title";
      public static final String  URL = "url";
      public static final String ID = "id";
       
      public CategoryLogic() {
        super(new CategoryDAL());
    }
      
      
      @Override
    public List<Category> getAll() {
        return get(()->dao().findAll());
    }
      
      @Override
    public Category getWithId(int id) {
         return get(()->dao().findById(id));    
    }
    
    public Category getWithUrl(String url){
          return get(()->dao().findByUrl(url)); 
    } 
    
    public Category getWithTitle(String title){
        return get(()->dao().findByTitle( title));
    } 
    
    public List<Category> search(String search){
         return get(()->dao().findContaining(search));
    }        

      @Override
    public Category createEntity(Map<String, String[]> parameterMap) {
         Category category = new Category();
         if(parameterMap.containsKey(ID)){
                 category.setId(Integer.parseInt(parameterMap.get(ID)[0]));
                 }
         if(parameterMap.containsKey(TITLE)){
                         if(parameterMap.get(TITLE)[0].isEmpty() || parameterMap.get(TITLE)[0].length() > 255){
                             throw  new ValidationException("Invalid Title");
                         }else{
                             category.setTitle(parameterMap.get(TITLE)[0]); 
                         }
                  }
         if(parameterMap.containsKey(URL)){
                         if(parameterMap.get(URL)[0].isEmpty() || parameterMap.get(URL)[0].length() >255){
                             throw new ValidationException("Invalid Url");
                         }else{
                              category.setUrl(parameterMap.get(URL)[0]);  
                         }
         }      
        return category;
     }       

    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "URL",  "TITLE");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList("id", "url",  "title");
    }

    @Override
    public List<?> extractDataAsList(Category e) {
        return Arrays.asList(e.getId(), e.getUrl(), e.getTitle());
    }       

    private Exception ValidationException(String invalid_Title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
