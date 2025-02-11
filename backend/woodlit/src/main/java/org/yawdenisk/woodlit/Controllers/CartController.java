package org.yawdenisk.woodlit.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.yawdenisk.woodlit.DTO.UserDetails;
import org.yawdenisk.woodlit.Entites.Cart;
import org.yawdenisk.woodlit.Entites.CartItem;
import org.yawdenisk.woodlit.Entites.User;
import org.yawdenisk.woodlit.Exceptions.CartNotFoundExcception;
import org.yawdenisk.woodlit.Exceptions.UserNotFoundException;
import org.yawdenisk.woodlit.Services.CartItemService;
import org.yawdenisk.woodlit.Services.CartService;
import org.yawdenisk.woodlit.Services.KeycloakService;
import org.yawdenisk.woodlit.Services.UserService;

import java.util.List;


@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private KeycloakService keycloakService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItem cartItem, @RequestHeader("Authorization") String token) {
        try {
            UserDetails userDetails = keycloakService.getUserDetails(token);
            User user = userService.findUserByEmail(userDetails.getEmail()).orElseThrow(() -> new UserNotFoundException());
            if(user.getCart()==null){
                Cart cart = new Cart();
                cart.setUser(user);
                user.setCart(cart);
                cartService.save(cart);
            }
            cartItem.setCart(user.getCart());
            cartItemService.saveItem(cartItem);
            return ResponseEntity.ok().body("Product added to cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while adding product to the cart" + e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String token) {
        try{
            UserDetails userDetails = keycloakService.getUserDetails(token);
            User user = userService.findUserByEmail(userDetails.getEmail()).orElseThrow(() -> new UserNotFoundException());
            Cart cart = cartService.getCartByUser(user).orElseThrow(() -> new CartNotFoundExcception());
            List<CartItem> cartItems = cart.getItems();
            return ResponseEntity.ok().body(cartItems);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while getting the cart");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItemFromCart(@PathVariable("id") String id) {
        try{
            cartItemService.deleteCartItem(id);
            return ResponseEntity.ok().body("Item deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while deleting the cartItem");
        }

    }
}
