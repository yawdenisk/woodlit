package org.yawdenisk.woodlit.Mappers;

import org.mapstruct.Mapper;
import org.yawdenisk.woodlit.Entites.User;
import org.yawdenisk.woodlit.Entites.UserResponce;

@Mapper
public interface UserMapper {
    UserResponce mapToUserResponce(User user);
}
