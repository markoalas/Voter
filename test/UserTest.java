import models.User;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import static org.hamcrest.core.Is.is;
import static play.test.Fixtures.deleteDatabase;

public class UserTest extends UnitTest {
    @Before
    public void before() {
        deleteDatabase();
        new User("Mati Kallas", "secret").save();
    }

    @Test
    public void retrieveUser() {
        User user = User.find("byName", "Mati Kallas").first();

        assertThat(user.name, is("Mati Kallas"));
        assertThat(user.password, is("secret"));
    }

    @Test
    public void tryConnectAsUser() {
        assertNotNull(User.connect("Mati Kallas", "secret"));
        assertNull(User.connect("Meelis", "secret"));
        assertNull(User.connect("Mati Kallas", "wrongSecret"));
    }
}
