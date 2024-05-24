import com.github.javafaker.Faker;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class User {
    private String email;
    private String password;
    private String name;

    public static User generic() {
        return new User("porovozik@toster.by", "p@ssword1", "TosterBY");
    }
    public static User random() {
        return new User("porovozik@toster.by", "p@ssword1", "TosterBY");
    }
}

