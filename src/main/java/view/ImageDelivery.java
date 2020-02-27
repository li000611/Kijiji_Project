/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author danal
 */
@WebServlet(name = "ImageDelivery", urlPatterns = {"/image/*"})
public class ImageDelivery extends HttpServlet {

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

        ServletContext cntx = request.getServletContext();
        // Get the absolute path of the image
        String filename = System.getProperty("user.home") + "/KijijiImages/"+ request.getPathInfo().substring(1) ;
        // retrieve mimeType dynamically
        String mimeType = cntx.getMimeType(filename);

        if (mimeType == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
        response.setContentType(mimeType);
        File file = new File(filename);
        try (
                FileInputStream in = new FileInputStream(file);
                OutputStream out = response.getOutputStream();) {
            if (file.exists()) {
                response.setContentType(mimeType);
                response.setContentLength((int) file.length());
                byte[] buf = new byte[1024];
                int count = 0;
                while ((count = in.read(buf)) >= 0) {
                    out.write(buf, 0, count);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
