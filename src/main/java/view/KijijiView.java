/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import common.FileUtility;
import entity.Category;
import entity.Image;
import entity.Item;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.CategoryLogic;
import logic.ImageLogic;
import logic.ItemLogic;
import scraper.kijiji.Kijiji;
import scraper.kijiji.KijijiItem;

/**
 *
 * @author Min Li
 */
@WebServlet(name = "KijijiView", urlPatterns = {"/KijijiView"})
public class KijijiView extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateItem</title>");
            out.println("<link rel=\"stylesheet\"  type=\"text/css\"  href=\"style/KijijiStyle.css\">");
            out.println("</head>");
            
            out.println("<body>");
            out.println("<div class=\"center-column\">");
            ItemLogic itl = new ItemLogic();
            List<Item> entity = itl.getAll();
            for (Item e : entity) {
                out.println("<div class=\"item\">");
                
                out.println("<div class=\"image\">");
                out.printf("<img src=\"image/%s.jpg\" style=\"max-width: 250px; max-height: 200px;\" />", e.getId());
                out.println("</div>");
                
                out.println("<div class=\"details\">");
                
                out.println("<div class=\"title\">");
                out.printf("<a href=\"%s\" target=\"_blank\">%s</a>", e.getUrl(), e.getTitle());
                out.println("</div>");
                
                out.println("<div class=\"price\">");
                if(e.getPrice() == null){
                    e.setPrice(BigDecimal.ZERO);
                }
                out.printf("price : %s", e.getPrice().toString());
                out.println("</div>");
                
                out.println("<div class=\"date\">");
                if(e.getDate()!=null)
                out.printf("date: %s", e.getDate().toString());
                out.println("</div>");
                
                
                out.println("<div class=\"location\">");
                out.printf("location: %s", e.getLocation());
                out.println("</div>");
                
                out.println("<div class=\"description\">");
                out.printf("description: %s", e.getDescription());
                out.println("</div>");
                
                
                out.println("</div>");
                out.println("</div>"); 
            }
            
            
               out.println("</div>");
               out.println("</body>");
               out.println("</html>");
               
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String directory = System.getProperty("user.home") + "/KijijiImages/";
        FileUtility.createDirectory(directory);
        Kijiji kijiji = new Kijiji();
        Category c = new CategoryLogic().getWithId(1);
        kijiji.downloadPage(c.getUrl());
        kijiji.findAllItems();
        ItemLogic itemLogic = new ItemLogic();
        ImageLogic imageLogic = new ImageLogic();
        kijiji.proccessItems((KijijiItem ki) -> {
            int id = Integer.parseInt(ki.getId());
            if (itemLogic.getWithId(id) != null) {
                return;
            }
            if (imageLogic.getWithUrl(ki.getImageUrl()).isEmpty()) {
                FileUtility.downloadAndSaveFile(ki.getImageUrl(), directory, ki.getId() + ".jpg");
            }
            Image image = imageLogic.getWithPath(directory + ki.getId() + ".jpg");
            if (image == null) {
                Map<String, String[]> sampleMap1 = new HashMap<>();
                sampleMap1.put(ImageLogic.NAME, new String[]{ki.getImageName()});
                sampleMap1.put(ImageLogic.PATH, new String[]{directory + ki.getId() + ".jpg"});
                sampleMap1.put(ImageLogic.URL, new String[]{ki.getImageUrl()});
                image = imageLogic.createEntity(sampleMap1);
                imageLogic.add(image);
            }
            Map<String, String[]> sampleMap2 = new HashMap<>();
            sampleMap2.put(ItemLogic.ID, new String[]{ki.getId()});
            sampleMap2.put(ItemLogic.PRICE, new String[]{ki.getPrice()});
            sampleMap2.put(ItemLogic.TITLE, new String[]{ki.getTitle()});
            sampleMap2.put(ItemLogic.DATE, new String[]{ki.getDate()});
            sampleMap2.put(ItemLogic.LOCATION, new String[]{ki.getLocation()});
            sampleMap2.put(ItemLogic.DESCRIPTION, new String[]{ki.getDescription()});
            sampleMap2.put(ItemLogic.URL, new String[]{ki.getUrl()});
            Item item = itemLogic.createEntity(sampleMap2);
            item.setImage(image);
            item.setCategory(c);
            itemLogic.add(item);
        });
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
