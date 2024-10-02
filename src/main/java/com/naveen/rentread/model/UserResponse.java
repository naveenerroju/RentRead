package com.naveen.rentread.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.naveen.rentread.domain.Rental;
import com.naveen.rentread.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
    Set<Rental> rentals;
}
