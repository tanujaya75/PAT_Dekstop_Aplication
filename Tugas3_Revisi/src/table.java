import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class table {


    private String ID;
    private String Email;
    private String Username;
    private String Password;
    private String Role;
    @JsonCreator
    public table(@JsonProperty("ID") String ID,
                 @JsonProperty("Email") String Email,
                 @JsonProperty("Username") String Username,
                 @JsonProperty("Password") String Password,
                 @JsonProperty("Role") String Role) {
        this.ID = ID;
        this.Email = Email;
        this.Username = Username;
        this.Password = Password;
        this.Role = Role;

    }
        public String getID () {
            return ID;
        }

        public void setID (String ID){
            this.ID = ID;
        }

        public String getEmail () {
            return Email;
        }

        public void setEmail (String Email){
            this.Email = Email;
        }

        public String getUsername () {
            return Username;
        }

        public void setUsername (String Username){
            this.Username = Username;
        }

        public String getPassword () {
            return Password;
        }

        public void setPassword (String Password){
            this.Password = Password;
        }

        public String getRole () {
            return Role;
        }

        public void setRole (String Role){
            this.Role = Role;
        }




}

