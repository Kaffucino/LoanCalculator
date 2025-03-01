package com.example.LoadCalculator.service;

import com.example.LoadCalculator.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> userList;

    public UserService() {

        this.userList = new ArrayList<>();

        User user1 = new User(1, "IDa", 32);
        User user2 = new User(2, "COpa", 31);
        User user3 = new User(3, "COfa", 30);

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

    }

    public Optional<User> getUser(Integer id)
    {
        Optional optional = Optional.empty();

        for (User user : userList)
        {
            if(id == user.getId())
            {
                optional = Optional.of(user);
                return optional;
            }
        }

        return optional;
    }
}
