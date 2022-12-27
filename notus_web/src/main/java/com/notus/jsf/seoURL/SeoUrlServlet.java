//package com.notus.jsf.seoURL;
//import java.io.IOException;
//import java.util.Arrays;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * @author Tunde Michael
// */
//@WebServlet(name = "SeoUrlServlet", urlPatterns = {"/"})
//public class SeoUrlServlet extends HttpServlet {
//
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 462511830549350937L;
//
//	/**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            String uri = request.getRequestURI();
//            String[] params = uri.split("/");
//            //System.out.println("URI --  " + uri);
//            //System.out.println("Params --  " + Arrays.toString(params));
//            // to extract the product name from the uri. We expect the 
//            // product name to be the last part of the uri
//            String productName = params[params.length - 1];
//            //System.out.println("Product Name -->  " + productName);
//            // let's extract the product ID from the product name
//            String productId = productName.substring(productName.lastIndexOf("-") + 1);
//            //System.out.println("PID -->  " + productId);
//            // It's good to check here if there is a valid product with this ID
//            // and if not redirect to list of products.
//            // if all goes well, let's render response here
//            request.getRequestDispatcher("/public/product.xhtml?pid=" + productId)
//                    .forward(request, response);
//
//        } catch (Exception e) {
//            // Handle exceptions to give your users safe landing
//            // expecially Array index out of bound exception 
//        }
//
//    }
//
//}