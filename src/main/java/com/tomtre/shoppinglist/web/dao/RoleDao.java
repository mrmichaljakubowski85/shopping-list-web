package com.tomtre.shoppinglist.web.dao;

import com.tomtre.shoppinglist.web.entity.Role;

public interface RoleDao {
    Role findRoleByName(String user);
}
