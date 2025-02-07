package org.yawdenisk.woodlit.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yawdenisk.woodlit.Entites.Cart;
import org.yawdenisk.woodlit.Entites.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
}
