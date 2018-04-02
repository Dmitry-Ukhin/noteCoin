package com.noteCoin.data.dao;

import com.noteCoin.models.User;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public interface UserDAO {
    Boolean save(User user);
    Boolean remove(User user);
    User update(User newUser);

    List<User> getList(String strQuery);
}
