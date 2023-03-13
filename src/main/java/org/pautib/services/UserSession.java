package org.pautib.services;

import javax.ejb.Stateful;
import java.io.Serializable;

@Stateful
public class UserSession implements Serializable {

    public String getCurrentUserName() {
        return "";
    }
}
