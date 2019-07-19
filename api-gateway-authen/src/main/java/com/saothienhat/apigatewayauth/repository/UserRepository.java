package com.saothienhat.apigatewayauth.repository;

import com.saothienhat.apigatewayauth.model.User;

//@Repository
//public interface UserRepository extends MongoRepository<User,String> {
//    @Query(value="{'email' : ?0}")
//    User findByEmail(String email);
//}
//@Repository
public interface UserRepository {
//extends CrudRepository<User, String> {
    User findByEmail(String email);
}