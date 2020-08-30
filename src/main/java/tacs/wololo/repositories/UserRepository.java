package tacs.wololo.repositories;

import tacs.wololo.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository
{
    private Map<String, User> userMap = new HashMap<>();

    public UserRepository()
    {
        userMap.put("test", new User("test", "test", "ADMIN"));
        userMap.put("user", new User("user", "pass", "USER"));
    }

    public User getUserbyUsername(String username)
    {
        return userMap.get(username);
    }
}
