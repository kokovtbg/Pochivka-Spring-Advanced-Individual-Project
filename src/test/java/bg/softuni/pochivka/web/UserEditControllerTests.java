package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.UserRegisterDTO;
import bg.softuni.pochivka.model.entity.User;
import bg.softuni.pochivka.model.user.PochivkaUserDetails;
import bg.softuni.pochivka.repository.UserRepository;
import bg.softuni.pochivka.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserEditControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;


    @Test
    @WithMockCustomUser(username = "petar", password = "12345", firstName = "Petar", lastName = "Petrov")
    public void testGetEditForm() throws Exception {
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUsername("petar");
        userDTO.setEmail("petar@abv.bg");
        userDTO.setPassword("12345");
        userDTO.setConfirmPassword("12345");
        userDTO.setFirstName("Petar");
        userDTO.setLastName("Petrov");
        userDTO.setTelephone("0888123456");
        Mockito.when(this.userService.getUserByUsername("petar"))
                .thenReturn(userDTO);
        mockMvc.perform(get("/user-edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-edit"));
    }

    @Test
    @WithMockCustomUser(username = "petar", password = "12345", firstName = "Petar", lastName = "Petrov")
    public void testPostEditForm() throws Exception {
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUsername("petar");
        userDTO.setEmail("petar@abv.bg");
        userDTO.setPassword("12345");
        userDTO.setConfirmPassword("12345");
        userDTO.setFirstName("Petar");
        userDTO.setLastName("Petrov");
        userDTO.setTelephone("0888123456");
        Mockito.when(this.userService.findUserByUsername("petar"))
                .thenReturn(false);
        Mockito.when(this.userService.getUserByUsername("petar"))
                .thenReturn(userDTO);
        Mockito.when(this.userService.findUserByEmail("petar@abv.bg"))
                .thenReturn(false);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                .thenReturn(true);
        PochivkaUserDetails pochivkaUserDetails = new PochivkaUserDetails("petar", "12345", "Petar", "Petrov", new ArrayList<>());
        Mockito.when(this.userService.update(userDTO, pochivkaUserDetails))
                .thenReturn(userDTO);
        mockMvc.perform(post("/user-edit").with(csrf())
                        .param("username", "petar")
                        .param("email", "petar@abv.bg")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("telephone", "0888123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user-profile"));
    }

    @Test
    @WithMockCustomUser(username = "petar", password = "12345", firstName = "Petar", lastName = "Petrov")
    public void testPostEditForm_BindingResultHasErrors() throws Exception {
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUsername("pet");
        userDTO.setEmail("petar@abv.bg");
        userDTO.setPassword("12345");
        userDTO.setConfirmPassword("12345");
        userDTO.setFirstName("Petar");
        userDTO.setLastName("Petrov");
        userDTO.setTelephone("0888123456");
        Mockito.when(this.userService.findUserByUsername("petar"))
                .thenReturn(false);
        Mockito.when(this.userService.getUserByUsername("petar"))
                .thenReturn(userDTO);
        Mockito.when(this.userService.findUserByEmail("petar@abv.bg"))
                .thenReturn(false);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                .thenReturn(true);
        PochivkaUserDetails pochivkaUserDetails = new PochivkaUserDetails("petar", "12345", "Petar", "Petrov", new ArrayList<>());
        Mockito.when(this.userService.update(userDTO, pochivkaUserDetails))
                .thenReturn(userDTO);
        mockMvc.perform(post("/user-edit").with(csrf())
                        .param("username", "pet")
                        .param("email", "petar@abv.bg")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("telephone", "0888123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user-edit"));
    }

    @Test
    @WithMockCustomUser(username = "petar", password = "12345", firstName = "Petar", lastName = "Petrov")
    public void testPostEditForm_UserByUsernameExists() throws Exception {
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUsername("petar123");
        userDTO.setEmail("petar@abv.bg");
        userDTO.setPassword("12345");
        userDTO.setConfirmPassword("12345");
        userDTO.setFirstName("Petar");
        userDTO.setLastName("Petrov");
        userDTO.setTelephone("0888123456");
        Mockito.when(this.userService.findUserByUsername("petar123"))
                .thenReturn(true);
        Mockito.when(this.userService.getUserByUsername("petar"))
                .thenReturn(userDTO);
        Mockito.when(this.userService.findUserByEmail("petar@abv.bg"))
                .thenReturn(false);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                .thenReturn(true);
        PochivkaUserDetails pochivkaUserDetails = new PochivkaUserDetails("petar", "12345", "Petar", "Petrov", new ArrayList<>());
        Mockito.when(this.userService.update(userDTO, pochivkaUserDetails))
                .thenReturn(userDTO);
        mockMvc.perform(post("/user-edit").with(csrf())
                        .param("username", "petar123")
                        .param("email", "petar@abv.bg")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("telephone", "0888123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user-edit"));
    }

    @Test
    @WithMockCustomUser(username = "petar", password = "12345", firstName = "Petar", lastName = "Petrov")
    public void testPostEditForm_UserByEmailExists() throws Exception {
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUsername("petar");
        userDTO.setEmail("petar@abv.bg");
        userDTO.setPassword("12345");
        userDTO.setConfirmPassword("12345");
        userDTO.setFirstName("Petar");
        userDTO.setLastName("Petrov");
        userDTO.setTelephone("0888123456");
        Mockito.when(this.userService.findUserByUsername("petar"))
                .thenReturn(false);
        Mockito.when(this.userService.getUserByUsername("petar"))
                .thenReturn(userDTO);
        Mockito.when(this.userService.findUserByEmail("petar1@abv.bg"))
                .thenReturn(true);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                .thenReturn(true);
        PochivkaUserDetails pochivkaUserDetails = new PochivkaUserDetails("petar", "12345", "Petar", "Petrov", new ArrayList<>());
        Mockito.when(this.userService.update(userDTO, pochivkaUserDetails))
                .thenReturn(userDTO);
        mockMvc.perform(post("/user-edit").with(csrf())
                        .param("username", "petar")
                        .param("email", "petar1@abv.bg")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("telephone", "0888123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user-edit"));
    }

    @Test
    @WithMockCustomUser(username = "petar", password = "12345", firstName = "Petar", lastName = "Petrov")
    public void testPostEditForm_PasswordNotMatching() throws Exception {
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUsername("petar");
        userDTO.setEmail("petar@abv.bg");
        userDTO.setPassword("12345");
        userDTO.setConfirmPassword("12345");
        userDTO.setFirstName("Petar");
        userDTO.setLastName("Petrov");
        userDTO.setTelephone("0888123456");
        Mockito.when(this.userService.findUserByUsername("petar"))
                .thenReturn(false);
        Mockito.when(this.userService.getUserByUsername("petar"))
                .thenReturn(userDTO);
        Mockito.when(this.userService.findUserByEmail("petar@abv.bg"))
                .thenReturn(false);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                .thenReturn(false);
        PochivkaUserDetails pochivkaUserDetails = new PochivkaUserDetails("petar", "12345", "Petar", "Petrov", new ArrayList<>());
        Mockito.when(this.userService.update(userDTO, pochivkaUserDetails))
                .thenReturn(userDTO);
        mockMvc.perform(post("/user-edit").with(csrf())
                        .param("username", "petar")
                        .param("email", "petar@abv.bg")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("telephone", "0888123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user-edit"));
    }
}
