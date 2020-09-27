package tacs.wololo.model;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import tacs.wololo.model.User;

public class UserTests {

    private User user;

    @Before
    public void init() {
        user = new User("josecito", "jose@gmail.com", "9394");
    }

    @Test
    public void unTest() {}

}
