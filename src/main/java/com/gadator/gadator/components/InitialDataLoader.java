package com.gadator.gadator.components;

import com.gadator.gadator.entity.Privilege;
import com.gadator.gadator.entity.Role;
import com.gadator.gadator.entity.User;
import com.gadator.gadator.repository.PrivilegeRepository;
import com.gadator.gadator.repository.RoleRepository;
import com.gadator.gadator.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>{

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        if (alreadySetup)
            return;

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege createPrivilege = createPrivilegeIfNotFound("CREATE_PRIVILEGE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, createPrivilege, deletePrivilege);
        List<Privilege> userPrivileges = Arrays.asList(readPrivilege, createPrivilege);

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        Role userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);

        User admin = createAdminIfNotFound(roleRepository.findByName("ROLE_ADMIN"));

        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String privilegeName)
    {
        Privilege privilege = privilegeRepository.findByName(privilegeName);

        if (privilege == null)
        {
            log.info("Creating privilege " + privilegeName);
            privilege = new Privilege();
            privilege.setName(privilegeName);
            privilegeRepository.save(privilege);
        }

        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(String roleName, List<Privilege> privileges)
    {
        Role role = roleRepository.findByName(roleName);

        if (role == null)
        {
            log.info("Creating role " + roleName);
            role = new Role();
            role.setName(roleName);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }

        return role;
    }

    @Transactional
    private User createAdminIfNotFound(Role adminRole)
    {
        User admin = userRepository.findOneByName("admin");

        if (admin == null)
        {
            log.info("Creating admin with role " + adminRole.getName());
            admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@here.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Arrays.asList(adminRole));
            userRepository.save(admin);
        }

        return admin;
    }
}
