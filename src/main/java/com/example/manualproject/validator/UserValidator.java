package com.example.manualproject.validator;

import com.example.manualproject.model.User;
import com.example.manualproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    private String email_regex = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public void validate(Object obj, Errors err) {
        checkIfEmpty(err);
        checkUserFields(obj, err);
    }

    public void checkIfEmpty(Errors err) {
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "error.name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "email", "error.email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "password", "error.password.empty");
    }

    public void checkUserFields(Object obj, Errors err) {
        User user = (User) obj;
        if (!user.getEmail().matches(email_regex)) err.rejectValue("email", "error.email.incorrect");
        if (user.getName().length() < 4 || user.getName().length() > 32) err.rejectValue("name", "error.name.size");
        if (userService.findByName(user.getName()) != null) err.rejectValue("name", "error.name.duplicate");
        if (userService.findByEmail(user.getEmail()) != null) err.rejectValue("email", "error.email.duplicate");
    }
}
