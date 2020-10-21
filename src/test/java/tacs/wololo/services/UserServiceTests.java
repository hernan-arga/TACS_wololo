package tacs.wololo.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import tacs.wololo.model.DTOs.UserInfoDto;
import tacs.wololo.model.User;
import tacs.wololo.repositories.UserRepository;
import tacs.wololo.services.implementations.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    private UserRepository repoMock;

    @InjectMocks
    private UserService userService;


    @Before
    public void init() {
        User catalina = mock(User.class);
        when(catalina.getUsername()).thenReturn("Catalina");
        when(catalina.getEmail()).thenReturn("catalina@hotmail.com");

        User pablo = mock(User.class);
        when(pablo.getUsername()).thenReturn("Pablo");
        when(pablo.getEmail()).thenReturn("pablo@hotmail.com");

        List<User> users = new ArrayList<>();
        users.add(catalina);
        users.add(pablo);

        Mockito.when(repoMock.findAll()).thenReturn(users);
    }

    @Test
    public void getUsersList()
    {

        List<UserInfoDto> users = userService.getUsersList();

        Assert.assertNotNull(users);
        Mockito.verify(repoMock, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repoMock);
    }
}
