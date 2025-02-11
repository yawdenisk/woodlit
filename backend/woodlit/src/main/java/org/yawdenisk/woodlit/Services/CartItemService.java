package org.yawdenisk.woodlit.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yawdenisk.woodlit.Entites.CartItem;
import org.yawdenisk.woodlit.Repositories.CartItemRepository;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    public void saveItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(String id) {
        cartItemRepository.deleteById(Long.valueOf(id));
    }
}
