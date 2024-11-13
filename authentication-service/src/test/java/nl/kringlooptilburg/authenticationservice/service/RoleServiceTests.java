package nl.kringlooptilburg.authenticationservice.service;

import nl.kringlooptilburg.authenticationservice.model.Role;
import nl.kringlooptilburg.authenticationservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleService roleService;


    @Test
    void testFindByName_Success() {
        // Arrange
        Role role = new Role();
        role.setName("ROLE_USER");

        when(roleService.findByName("ROLE_USER")).thenReturn(role);

        // Act
        Role foundRole = roleService.findByName("ROLE_USER");

        // Assert
        assertNotNull(foundRole, "Role should not be null");
        assertEquals("ROLE_USER", foundRole.getName(), "Role name should match 'ROLE_USER'");
    }



    @Test
    void testSetupRoles_CreateRolesIfNotFound() {
        // Arrange
        Role createdRoleUser = new Role();
        createdRoleUser.setName("ROLE_USER");
        Role createdRoleAdmin = new Role();
        createdRoleAdmin.setName("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.empty());

        // Act
        roleService = new RoleService(roleRepository);

        // Assert
        assertNotNull(createdRoleUser, "ROLE_USER should be created");
        assertNotNull(createdRoleAdmin, "ROLE_ADMIN should be created");
        assertEquals("ROLE_USER", createdRoleUser.getName(), "Created role should be 'ROLE_USER'");
        assertEquals("ROLE_ADMIN", createdRoleAdmin.getName(), "Created role should be 'ROLE_ADMIN'");
    }


    @Test
    void testSetupRoles_RolesAlreadyExist() {
        // Arrange
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(roleAdmin));

        // Act
        roleService = new RoleService(roleRepository);

        // Assert
        assertNotNull(roleUser, "ROLE_USER should already exist");
        assertNotNull(roleAdmin, "ROLE_ADMIN should already exist");
        assertEquals("ROLE_USER", roleUser.getName(), "Existing role should be 'ROLE_USER'");
        assertEquals("ROLE_ADMIN", roleAdmin.getName(), "Existing role should be 'ROLE_ADMIN'");
    }
}
