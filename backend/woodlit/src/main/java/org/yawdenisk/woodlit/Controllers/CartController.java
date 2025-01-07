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
    public ResponseEntity<?> addItemToCart(Map<String, Object> request) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(authentication.getName()).orElseThrow(() -> new UserNotFoundException());
            Cart cart = cartService.findCartByUser(user).orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setUser(user);
                        cartService.save(newCart);
                        return newCart;
                    });
            Long productId = Long.valueOf(request.get("productId").toString()); // Убедимся, что это Long
            Long quantity = Long.valueOf(request.get("quantity").toString());
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                Optional<Product> product = productService.getProductById(productId);
                Product p = product.get();
                cartItem.setProduct(p);
                cart.getItems().add(cartItem);
                cartService.save(cart);
            return ResponseEntity.ok().body("Product added to cart successfully");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
