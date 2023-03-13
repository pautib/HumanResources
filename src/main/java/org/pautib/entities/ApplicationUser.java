package org.pautib.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

@Entity
@Table(name = "USER")
@NamedQuery(name = ApplicationUser.FIND_USER_BY_CREDENTIALS, query = "select u from ApplicationUser u where u.email = :email")
public class ApplicationUser extends AbstractEntity {

    public static final String FIND_USER_BY_CREDENTIALS = "User.findUserByCredentials";
    @NotEmpty(message = "Email must be set")
    @Email(message = "The email must be set in the form user@domain.com")
    @FormParam("email") // Needed for adding user with @BeanParam
    private String email;

    @NotEmpty(message = "Password must be set")
    @Size(min = 8)
    //@Pattern(regexp = "", message = "Password must be in the form...")
    @FormParam("password") // Needed for adding user with @BeanParam
    private String password;

    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
}
