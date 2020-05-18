/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraper.kijiji;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Min Li
 */
public class ItemBuilder {
    
    private static final String URL_BASE = "https://www.kijiji.ca";
    
    private static final String ATTRIBUTE_ID = "data-listing-id";
    private static final String ATTRIBUTE_IMAGE = "image";
    private static final String ATTRIBUTE_IMAGE_SRC = "data-src";
    private static final String ATTRIBUTE_IMAGE_NAME = "alt";
    private static final String ATTRIBUTE_PRICE = "price";
    private static final String ATTRIBUTE_TITLE = "title";
    private static final String ATTRIBUTE_LOCATION = "location";
    private static final String ATTRIBUTE_DATE = "date-posted";
    private static final String ATTRIBUTE_DESCRIPTION = "description";
    
    private Element element;
    private KijijiItem item;
    
    ItemBuilder() {
        this.item = new KijijiItem();
    }
    
    public ItemBuilder setElement(Element element) {
        this.element = element;
        return this;
    }
    
    public KijijiItem build() {
        Elements image = element.getElementsByClass(ATTRIBUTE_IMAGE);
        Elements id = element.getElementsByClass(ATTRIBUTE_ID);
        Elements price = element.getElementsByClass(ATTRIBUTE_PRICE);
        Elements title = element.getElementsByClass(ATTRIBUTE_TITLE);
        Elements location = element.getElementsByClass(ATTRIBUTE_LOCATION);
        Elements date = element.getElementsByClass(ATTRIBUTE_DATE);
        Elements description = element.getElementsByClass(ATTRIBUTE_DESCRIPTION);
        
//         if (image.isEmpty()) {
//             item.setImageUrl("");
//         } else {
//             item.setImageUrl(image.get(0).child(0).attr(ATTRIBUTE_IMAGE_SRC).trim());
//         }
        
        item.setUrl(URL_BASE + element.getElementsByClass(ATTRIBUTE_TITLE).get(0).child(0).attr("href").trim());
        item.setImageUrl(getImageUrl());
        item.setId(element.attr(ATTRIBUTE_ID).trim());
        item.setImageName(getImageName());
        
//         if (image.isEmpty()) {
//             item.setImageName("");
//         } else {
//             item.setImageName(image.get(0).child(0).attr(ATTRIBUTE_IMAGE_NAME).trim());
//         }
        
        if (price.isEmpty()) {
            item.setPrice("");
        } else {
            item.setPrice(price.get(0).text().trim());
        }
        
        if (title.isEmpty()) {
            item.setTitle("");
        } else {
            item.setTitle(title.get(0).child(0).text().trim());
        }
        
        if (location.isEmpty()) {
            item.setLocation("");
        } else {
            item.setLocation(location.get(0).childNode(0).outerHtml().trim());
        }
        
        if (date.isEmpty()) {
            item.setDate("");
        } else {
            item.setDate(date.get(0).text().trim());
        }
        
        if (description.isEmpty()) {
            item.setDescription("");
        } else {
            item.setDescription(description.get(0).text().trim());
        }
        return item;
    }
}
