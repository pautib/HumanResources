package org.pautib.services;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class ApplicationState implements Serializable {

    private String email;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
