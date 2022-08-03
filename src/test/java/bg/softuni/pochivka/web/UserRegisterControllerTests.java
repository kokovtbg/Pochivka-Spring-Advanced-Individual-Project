package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.UserRegisterDTO;
import bg.softuni.pochivka.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void testGetRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-register"));
    }

    @Test
    public void testRegister() throws Exception {
        Mockito.when(this.userService.findUserByUsername("petar"))
                        .thenReturn(false);
        Mockito.when(this.userService.findUserByEmail("petar@abv.bg"))
                        .thenReturn(false);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                        .thenReturn(true);
        mockMvc.perform(post("/register")
                .param("username", "petar")
                .param("email", "petar@abv.bg")
                .param("firstName", "Petar")
                .param("lastName", "Petrov")
                .param("password", "12345")
                .param("confirmPassword", "12345")
                .param("telephone","0888888888")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testRegister_whenBindingResultHasErrors() throws Exception {
        Mockito.when(this.userService.findUserByUsername("petar"))
                .thenReturn(false);
        Mockito.when(this.userService.findUserByEmail("petar@abv.bg"))
                .thenReturn(false);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                .thenReturn(true);
        mockMvc.perform(post("/register")
                        .param("username", "pet")
                        .param("email", "petar@abv.bg")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("telephone","0888888888")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
    }

    @Test
    public void testRegister_whenUserByUsernameExist() throws Exception {
        Mockito.when(this.userService.findUserByUsername("petar"))
                .thenReturn(true);
        Mockito.when(this.userService.findUserByEmail("petar@abv.bg"))
                .thenReturn(false);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                .thenReturn(true);
        mockMvc.perform(post("/register")
                        .param("username", "petar")
                        .param("email", "petar@abv.bg")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("telephone","0888888888")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
    }

    @Test
    public void testRegister_whenUserByEmailExist() throws Exception {
        Mockito.when(this.userService.findUserByUsername("petar"))
                .thenReturn(false);
        Mockito.when(this.userService.findUserByEmail("petar@abv.bg"))
                .thenReturn(true);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                .thenReturn(true);
        mockMvc.perform(post("/register")
                        .param("username", "petar")
                        .param("email", "petar@abv.bg")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("telephone","0888888888")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
    }

    @Test
    public void testRegister_whenPasswordsNotMatch() throws Exception {
        Mockito.when(this.userService.findUserByUsername("petar"))
                .thenReturn(false);
        Mockito.when(this.userService.findUserByEmail("petar@abv.bg"))
                .thenReturn(false);
        Mockito.when(this.userService.passwordsMatch("12345", "12345"))
                .thenReturn(false);
        mockMvc.perform(post("/register")
                        .param("username", "petar")
                        .param("email", "petar@abv.bg")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("telephone","0888888888")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
    }
}
