package dataLayer;

import java.io.Serializable;

/**
 * Class wrap an account
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String username;
    private String password;
    private int privileges;

    public Account(String username, String password, int privileges)
    {
        this.username = username;
        this.password = password;
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Account))
            return false;
        Account ac2 = (Account) obj;
        return this.username.contentEquals(ac2.username) &&
                this.password.contentEquals(ac2.password) &&
                this.privileges == ac2.privileges;
    }

    @Override
    public String toString()
    {
        return "username:" + username + " password: " + password + " privileges: " + privileges;
    }
}
