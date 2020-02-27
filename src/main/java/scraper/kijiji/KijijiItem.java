/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraper.kijiji;

import java.util.Objects;

/**
 *
 * @author Min Li
 */

public class KijijiItem {
    private  String id;
    private  String url;
    private  String imageUrl;
    private  String imageName;
    private  String price;
    private  String title;
    private  String date;
    private  String location;
    private  String description;
  
    KijijiItem (){
    }
    
    void setUrl(String url){
        this.url = url;
    }
    
    public String getUrl(){
        return url;
    }
  
     void setId(String id){
        this.id = id;
    }
     
    public String getId(){
        return id;
    }
    
    void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    
    public String getImageUrl(){
        return imageUrl;
    }
    
    void setImageName(String imageName){
        this.imageName = imageName;
    }
    
    public String getImageName(){
        return imageName;
    }
    
    void setPrice(String price){
        this.price = price;
    }
    
    public String getPrice(){
         return price;
    }
    
    void setTitle(String title){
        this.title = title;
    }
    
    public String getTitle(){
        return title;
    }
    
    void setDate(String date){
        this.date = date;
    }
    
    public String getDate(){
        return date;
    }
    
    void setLocation(String location){
        this.location = location;
    }
    
    public String getLocation(){
        return location;
    }
    
    void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
    
   @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KijijiItem other = (KijijiItem) obj;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public String toString() {
        return String.format("[id:%s, image_url:%s, image_name:%s, price:%s, title:%s, date:%s, location:%s, description:%s]",
                getId(), getImageUrl(), getImageName(), getPrice(), getTitle(), getDate(), getLocation(), getDescription());
    }

    void setImageSrc(String trim) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
