package ro.proiecte.blogapp.dto;
// aici stabilim ce date transferam intre web si backend. se mai cheama si DTO (data transfer object) class
public class RegisterRequest {

    public String username;
    public String password;
    public String email;

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
