package org.yawdenisk.woodlit.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yawdenisk.woodlit.Entites.Cart;
import org.yawdenisk.woodlit.Entites.CartItem;
import org.yawdenisk.woodlit.Entites.Product;
import org.yawdenisk.woodlit.Entites.User;
import org.yawdenisk.woodlit.Exceptions.ProductNotFoundException;
import org.yawdenisk.woodlit.Exceptions.UserNotFoundException;
import org.yawdenisk.woodlit.Services.CartService;
import org.yawdenisk.woodlit.Services.ProductService;
import org.yawdenisk.woodlit.Services.UserService;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItem cartItem) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException());
            Cart cart = cartService.findCartByUser(user).orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setUser(user);
                        cartService.save(newCart);
                        return newCart;
                    });
            Product product = productService.getProductById(cartItem.getProduct().getId()).orElseThrow(() -> new ProductNotFoundException());
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
            cartService.save(cart);
            return ResponseEntity.ok().body("Product added to cart successfully");
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error while adding product to the cart");
        }
    }
}
