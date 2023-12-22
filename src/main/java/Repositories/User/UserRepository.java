package Repositories.User;

import java.util.List;

public interface UserRepository {

    List findById(long userId);
    List findAll();


}
