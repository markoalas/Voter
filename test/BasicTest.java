import models.Topic;
import models.User;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import static org.hamcrest.core.Is.is;
import static play.test.Fixtures.deleteDatabase;

public class BasicTest extends UnitTest {

    private User mati;
    private Topic topic;

    @Before
    public void before() {
        deleteDatabase();
        mati = new User("Mati Kallas", "secret").save();
        topic = new Topic(mati, "Functional programming", "Talk something about functional programming", "anyone").save();
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

    @Test
    public void addNewTopic() {
        mati.addTopic(topic);

        assertThat(mati.topics.size(), is(1));
        Topic topic = mati.topics.get(0);

        assertThat(topic.title, is("Functional programming"));
        assertThat(topic.description, is("Talk something about functional programming"));
        assertThat(topic.proposedSpeaker, is("anyone"));
        assertNotNull(topic.createdAt);
    }
    
    @Test
    public void vote() {
        topic.save();

        topic.votedBy(mati, 1);

        assertThat(topic.votes.size(), is(1));
        assertThat(mati.votes.size(), is(1));
    }

}
