package org.yawdenisk.woodlit.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yawdenisk.woodlit.Entites.Cart;
import org.yawdenisk.woodlit.Entites.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUser(User user);
}
