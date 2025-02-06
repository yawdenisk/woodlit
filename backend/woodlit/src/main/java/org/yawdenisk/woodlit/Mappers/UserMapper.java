package org.yawdenisk.woodlit.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.ResponseEntity;
import org.yawdenisk.woodlit.DTO.UserDetails;
import org.yawdenisk.woodlit.Entites.User;

@Mapper
public interface UserMapper {
    @Mapping(target = "cart", ignore = true)
    User userDetailsToUser(UserDetails userDetails);
}
