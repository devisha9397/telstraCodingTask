package com.telstra.codechallenge.oldestuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/oldUser", method = RequestMethod.GET)
    public ResponseEntity<Object> oldUser(@RequestParam(value = "limit", defaultValue = "30") int limit) throws Exception {
       return new ResponseEntity<>(userService.getUsersWithZeroFollowers(limit), HttpStatus.OK);
    }
}
