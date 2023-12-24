package com.moviebookingapp.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.moviebookingapp.models.Role;



@Repository
public interface RoleDao extends MongoRepository<Role, String>{

}
