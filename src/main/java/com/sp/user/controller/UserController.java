package com.sp.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sp.user.model.Message;
import com.sp.user.model.Phone;
import com.sp.user.model.User;
import com.sp.user.model.UserReturn;
import com.sp.user.repository.UserRepository;
import com.sp.user.utils.CreateJWT;
import com.sp.user.utils.ValidateEmail;

import io.jsonwebtoken.Claims;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger();

  @Autowired
  UserRepository userRepository;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
    try {
      List<User> users = new ArrayList<User>();

      
  	  logger.info("name: "+name);
  	
      if (name == null)
    	  userRepository.findAll().forEach(users::add);
      else
    	  userRepository.findByNameContainingIgnoreCase(name).forEach(users::add);
      logger.info("users: "+users);
      
      if (users.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
    	logger.info("e.getMessage(): "+e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
    Optional<User> userData = userRepository.findById(id);

    if (userData.isPresent()) {
      return new ResponseEntity<>(userData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/users")
  //public ResponseEntity<User> createUser(@RequestBody User user) {
  public String createUser(@RequestBody User user) {
	  Message message;
	  Gson gson = new Gson();
    try {
    	
    	String jwtId = user.getPassword();
        String jwtIssuer = user.getEmail();
        String jwtSubject = user.getName();
        int jwtTimeToLive = 800000;

        String jwt = CreateJWT.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );
        
        logger.info("jwt = \"" + jwt.toString() + "\"");

        //Claims claims = CreateJWT.decodeJWT(jwt);

        //logger.info("claims = " + claims.toString());        
    	    	
    	User userIn = new User(user.getName(), user.getEmail(), user.getPassword(), jwt.toString(), true, user.getPhones());
    	if(!ValidateEmail.isValidateEmail(userIn.getEmail())) {
    		message = new Message("ERROR. El correo de este Usuario no tiene el formato correcto");
			logger.info("message: "+message);
			logger.info("gson.toJson(message): "+gson.toJson(message));
			return gson.toJson(message).toString();
    	}
    	List<User> listUserCorreExiste = findByEmail(userIn.getEmail());
    	if (listUserCorreExiste.size()>0) {
    		message = new Message("ERROR. El correo de este Usuario ya existe en la Base d Datos");
			logger.info("message: "+message);
			logger.info("gson.toJson(message): "+gson.toJson(message));
			return gson.toJson(message).toString();
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    	
    	// list of Phone
        List<Phone> phones = new ArrayList<>();
        for (Phone phoneIn : user.getPhones()) {
            // new Phone
        	Phone phone = new Phone(phoneIn.getNumber(), phoneIn.getCitycode(), phoneIn.getContrycode());
            // set user to Phone
        	phone.setUser(userIn);
            // add Phone to list
        	phones.add(phone);
        }
       
     // add phone list to User
        userIn.setPhones(phones);
        
      User _user = userRepository.save(userIn);
      logger.info("_user: "+_user);
      
      ResponseEntity<User> responseUser = new ResponseEntity<>(_user, HttpStatus.CREATED);           
      
      
		if(responseUser.getStatusCodeValue()==HttpStatus.CREATED.value()) {
			UserReturn userReturn = new UserReturn(_user.getId(),_user.getDateCreate(),_user.getDateModified(),_user.getDateLastLogin(),jwt.toString(),_user.getActive());
			//message = new Message("Datos Guardados correctamente con Estatus: "+responseUser.getStatusCode());
			logger.info("userReturn: "+userReturn);
			return gson.toJson(userReturn).toString();
		}
		else {
			message = new Message("ERROR en la insercion de los Datos con Estatus: "+responseUser.getStatusCode());
			logger.info("message: "+message);
			logger.info("gson.toJson(message): "+gson.toJson(message));
			return gson.toJson(message).toString();
		}
		
		
    } catch (Exception e) {
    	message = new Message("Excepcion arrojada: "+e.getMessage());
      //return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	//return HttpStatus.INTERNAL_SERVER_ERROR.toString();
    	return gson.toJson(message);
    }
  }
//13202
  @PutMapping("/users/{id}")
  public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
    Optional<User> userData = userRepository.findById(id);

    if (userData.isPresent()) {
      User _user = userData.get();
      _user.setName(user.getName());
      _user.setEmail(user.getEmail());
      _user.setPassword(user.getPassword());
      _user.setActive(user.getActive());
      return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
    try {
    	userRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/users")
  public ResponseEntity<HttpStatus> deleteAllUsers() {
    try {
    	userRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/users/published")
  public ResponseEntity<List<User>> findByActive() {
    try {
      List<User> users = userRepository.findByActive(true);

      if (users.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  public List<User> findByEmail(String email) {
	    try {
	      List<User> users = userRepository.findByEmailContainingIgnoreCase(email);

	      return users;
	    } catch (Exception e) {
	      return null;
	    }
	  }

}
