package com.my_project.niit_final_project.controllers.client;

import com.my_project.niit_final_project.entities.CartProduct;
import com.my_project.niit_final_project.entities.Product;
import com.my_project.niit_final_project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

@Controller
@RequestMapping("/client/cart")
public class ClientCartController {
    @Autowired
    private ProductService productService;

    @GetMapping("/get-total-cart-items")
    @ResponseBody
    private String getTotalCartItem(HttpSession session) {
        if (session.getAttribute("CART") != null) {
            ArrayList<CartProduct> cartProducts = (ArrayList<CartProduct>) session.getAttribute("CART");
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.HALF_DOWN);
            double totalPrice = productService.calculateTotalPrice(cartProducts);
            totalPrice= Double.parseDouble(df.format(totalPrice));
            session.setAttribute("TOTAL_PRICE", totalPrice);
            return "{\"total_cart_items\":" + ((ArrayList<CartProduct>) session.getAttribute("CART")).size() + "}";
        }
        session.setAttribute("TOTAL_PRICE", 0);
        return "{\"total_cart_items\":0}";
    }

    @PostMapping("/add-to-cart")
    @ResponseBody
    private ResponseEntity<?> addToCart(@RequestParam(name = "id") Long id,
                                        @RequestParam(name = "quantity") int quantity,
                                        HttpSession session) {
        ArrayList<CartProduct> cartProducts;
        if (session.getAttribute("CART") == null) {

            cartProducts = new ArrayList<>();
            Product product = productService.getProductById(id);
            CartProduct cartProduct = new CartProduct();
            cartProduct.setId(id);
            cartProduct.setName(product.getName());
            double price=0;
            if( product.getVoucher()!=null){
                price= (product.getPrice()-product.getPrice()* (product.getVoucher().getSalePercentage()/100));
            }else {
                price= (price + product.getPrice());
            }
            cartProduct.setPrice(price);
            cartProduct.setPicture(product.getPicture());
            cartProduct.setQuantity(quantity);
            cartProducts.add(cartProduct);
            session.setAttribute("CART", cartProducts);

        } else {
            cartProducts = (ArrayList<CartProduct>) session.getAttribute("CART");
            boolean check = false;
            for (CartProduct cartProduct : cartProducts) {

                if (cartProduct.getId() == id) {
                    cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
                    check = true;
                    break;
                }
            }
            if (!check) {
                CartProduct cartProduct = new CartProduct();
                Product product = productService.getProductById(id);
                cartProduct.setId(id);
                cartProduct.setName(product.getName());
                double price=0;
                if( product.getVoucher()!=null){
                    price= (product.getPrice()-product.getPrice()* (product.getVoucher().getSalePercentage()/100));
                }else {
                    price= (price + product.getPrice());
                }
                cartProduct.setPrice(price);
                cartProduct.setPicture(product.getPicture());
                cartProduct.setQuantity(quantity);
                cartProducts.add(cartProduct);


            }

            session.setAttribute("CART", cartProducts);

        }

        return ResponseEntity.ok(cartProducts);
    }

    @PostMapping("/product/delete")
    @ResponseBody
    public ResponseEntity<?> getCart(HttpSession session,
                                     @RequestParam(name = "id") long id
    ) {
        ArrayList<CartProduct> cartProducts = (ArrayList<CartProduct>) session.getAttribute("CART");
        ArrayList<CartProduct> tmpCartProducts = new ArrayList<>();
        for (int i = 0; i < cartProducts.size(); i++) {
            if (cartProducts.get(i).getId() != id) {
                tmpCartProducts.add(cartProducts.get(i));
            }
        }
        session.setAttribute("CART", tmpCartProducts);
        return ResponseEntity.ok(tmpCartProducts);
    }

    @GetMapping("/get-cart")
    @ResponseBody
    public ResponseEntity<?> getCart(HttpSession session) {
        if (session.getAttribute("CART") == null) {
            return ResponseEntity.ok(new ArrayList<CartProduct>());
        }
        ArrayList<CartProduct> cartProducts = (ArrayList<CartProduct>) session.getAttribute("CART");
        return ResponseEntity.ok(cartProducts);

    }

    @PostMapping("/change-quantity")
    @ResponseBody
    public ResponseEntity<?> changedQuantity(@RequestParam(name = "id") long id, @RequestParam(name = "step") int step, HttpSession session) {
        ArrayList<CartProduct> cartProducts = (ArrayList<CartProduct>) session.getAttribute("CART");
        for (int i = 0; i < cartProducts.size(); i++) {
            if (cartProducts.get(i).getId() == id) {
                cartProducts.get(i).setQuantity(cartProducts.get(i).getQuantity() + step);
            }
        }
        session.setAttribute("CART", cartProducts);
        return ResponseEntity.ok(cartProducts);
    }

    @GetMapping("/get-mini-cart")
    @ResponseBody
    public ResponseEntity<?> getMiniCart(HttpSession session) {
        if (session.getAttribute("CART") == null) {
            return ResponseEntity.ok(new ArrayList<CartProduct>());
        }
        ArrayList<CartProduct> cartProducts = (ArrayList<CartProduct>) session.getAttribute("CART");
        return ResponseEntity.ok(cartProducts);
    }

    @GetMapping("/page")
    public String getPage() {
        return "/client/cart";
    }
}
