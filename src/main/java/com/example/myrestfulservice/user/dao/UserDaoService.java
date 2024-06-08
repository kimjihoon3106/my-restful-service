package com.example.myrestfulservice.user.dao;

import com.example.myrestfulservice.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static{
        users.add(new User(1, "kenneth", new Date(), "test1", "11111-111111"));
        users.add(new User(2, "Alice", new Date(),"test2", "22222-111111"));
        users.add(new User(3, "Elena", new Date(),"test3", "33333-111111"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }

        if(user.getJoinDate() == null) {
            user.setJoinDate(new Date());
        }

        users.add(user);

        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()) {
            User user = iterator.next();

            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
