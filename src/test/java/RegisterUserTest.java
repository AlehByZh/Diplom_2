import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class RegisterUserTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();

    @Test
    public void registerPositiveTest() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        String created = check.createdSuccessfully(createResponse);

        ValidatableResponse deleteResponse = client.deleteUser(created);
        check.deleteSuccessFully(deleteResponse);
    }
}