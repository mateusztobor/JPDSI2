//package com.notus.jsf.seoURL;
//import java.io.Serializable;
//import javax.faces.view.ViewScoped;
//import javax.inject.Named;
//
///**
// *
// * @author Tunde Michael
// *
// */
//@Named(value = "seoURLController")
//@ViewScoped
//public class SEOURLController implements Serializable {
//
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = -7872500645040368104L;
//	private String text;
//    private String seoUrl;
//    private String productId;
//
//    /**
//     * This method converts just about any text
//     * to it's SEO friendly equivalent
//     */
//    public void convert() {
//        if (text != null && !text.isEmpty()) {
//            // trim spaces at the edges, convert all to lowercase and
//            // remove all non-alpha numeric characters
//            String url = text.trim().toLowerCase().replaceAll("[^a-z0-9_\\s-]", ""); 
//            // change all multiple white spaces to single white space
//            url = url.replaceAll("[\\s-]+", " ");
//            // replace all the single white spaces with a dash
//            seoUrl = url.replaceAll("[\\s]", "-");
//            // at this point we will save the product in the database 
//            // with SEO friendly URL appended with the product ID at the end
//            // Let's say the product ID is 12345
//            Long productId = 12345L;
//            seoUrl += "-" + productId; 
//            System.out.println("SEO Friendly URL -->  " + seoUrl);
//        } else {
//            seoUrl = "Please enter text to convert";
//        }
//    }
//    
//    /**
//     * The pre-render view method to retrieve the 
//     * product details from the database using the 
//     * product ID
//     */
//    public void retrieveProductFromDatabase(){
//        // retrieve product from the database here
//        System.out.println("Product Retrieved using ID -->  " + this.productId);
//    
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public String getSeoUrl() {
//        return seoUrl;
//    }
//
//    public void setSeoUrl(String seoUrl) {
//        this.seoUrl = seoUrl;
//    }
//
//    public String getProductId() {
//        return productId;
//    }
//
//    public void setProductId(String productId) {
//        this.productId = productId;
//    }
//
//}