// User.java
public abstract class User {
    protected String username;
    protected String password;
    protected String name;
    protected String email;

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name     = name;
        this.email    = email;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
