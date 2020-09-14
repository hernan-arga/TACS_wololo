package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.DTOs.ProvinceInfoDto;
import tacs.wololo.model.DTOs.UserInfoDto;
import tacs.wololo.model.User;
import tacs.wololo.repositories.UserRepository;
import tacs.wololo.services.IUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    public UserService() {

    }

    public List<UserInfoDto> getUsersList() {
        List<UserInfoDto> usersInfoDto = new ArrayList<UserInfoDto>();
        userRepository.findAll().forEach(user ->
                usersInfoDto.add(new UserInfoDto(user.getUsername(), user.getEmail())));
        return usersInfoDto;
    }

}
