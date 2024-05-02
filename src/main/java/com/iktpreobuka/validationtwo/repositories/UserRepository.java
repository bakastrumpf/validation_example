package com.iktpreobuka.validationtwo.repositories;

import org.springframework.data.repository.CrudRepository;
import com.iktpreobuka.validationtwo.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer>{

}
