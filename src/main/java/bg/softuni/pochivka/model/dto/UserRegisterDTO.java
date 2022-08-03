package bg.softuni.pochivka.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterDTO {
    @NotBlank(message = "Потребителското име не може да бъде пропуснато")
    @Size(min = 5, max = 20, message = "Потребителското име трябва да бъде между 5 и 20 символа")
    private String username;
    @NotBlank(message = "Електронната поща не може да бъде пропусната")
    @Email(message = "Електронната поща трябва да съдържа @")
    private String email;
    @NotBlank(message = "Паролата не може да бъде пропусната")
    @Size(min = 5, max = 20, message = "Паролата трябва да бъде между 5 и 20 символа")
    private String password;
    @NotBlank(message = "Паролата не може да бъде пропусната")
    @Size(min = 5, max = 20, message = "Паролата трябва да бъде между 5 и 20 символа")
    private String confirmPassword;
    @NotBlank(message = "Името не може да бъде пропуснато")
    @Size(max = 20, message = "Името не може да бъде по-дълго от 20 символа")
    @Pattern(regexp = "[A-Za-zА-Яа-я]+", message = "Името трябва да съдържа букви")
    private String firstName;
    @NotBlank(message = "Името не може да бъде пропуснато")
    @Size(max = 20, message = "Името не може да бъде по-дълго от 20 символа")
    @Pattern(regexp = "[A-Za-zА-Яа-я]+", message = "Името трябва да съдържа букви")
    private String lastName;
    @NotBlank(message = "Телефонът не може да бъде пропуснат")
    @Pattern(regexp = "\\d+|\\+\\d+", message = "Телефонът трябва да е валиден")
    @Size(max = 15, message = "Телефонът трябва да е валиден")
    private String telephone;
    private String town;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
