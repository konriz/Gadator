package com.gadator.users.services;

import com.gadator.users.entities.Privilege;
import com.gadator.users.entities.Role;
import com.gadator.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

//    TODO move this to UserService?

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findOneByName(username);
        if (user == null)
        {
            throw new UsernameNotFoundException(
                    "No user found with username: " + username
            );
        }

// TODO implement this into user
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(), getAuthorities(user.getRoles()));

    }

    private List<GrantedAuthority> getAuthorities (List<Role> roles)
    {
        return getGrantedAuthorities(getPrivileges(roles));
    }


    private List<String> getPrivileges(List<Role> roles)
    {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles)
        {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege privilege : collection)
        {
            privileges.add(privilege.getName());
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges)
    {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String privilege : privileges)
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(privilege));
        }

        return grantedAuthorities;
    }
}
