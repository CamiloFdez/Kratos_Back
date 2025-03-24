package eci.escuelaing.edu.co.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;
    private String rol;
    private String name;
    private String email;
    private String password;

    public Usuario() {}

    public Usuario(String id, String name, String email, String password, String rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }

    public void setRol(String rol) { this.rol = rol; }

}



