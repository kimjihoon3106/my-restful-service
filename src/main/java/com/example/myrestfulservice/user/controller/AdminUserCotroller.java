package com.example.myrestfulservice.user.controller;

import com.example.myrestfulservice.user.dao.UserDaoService;
import com.example.myrestfulservice.user.domain.AdminUser;
import com.example.myrestfulservice.user.domain.User;
import com.example.myrestfulservice.user.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminUserCotroller {

    private UserDaoService service;

    public AdminUserCotroller(UserDaoService service) {
        this.service = service;
    }

    // /admin/users/{id}
    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUserAdmin(@PathVariable int id) {
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }
}
