import models.Topic;
import models.User;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import static models.Vote.Direction.DOWN;
import static models.Vote.Direction.UP;
import static org.hamcrest.core.Is.is;
import static play.test.Fixtures.deleteDatabase;

public class TopicTest extends UnitTest {
    private User mati;
    private User mihkel;
    private Topic topic;

    @Before
    public void before() {
        deleteDatabase();
        mati = new User("Mati Kallas", "secret").save();
        mihkel = new User("Mihkel Mets", "secret").save();
        topic = new Topic(mati, "Functional programming", "Talk something about functional programming", "anyone").save();
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
    public void voteUp() {
        topic.votedBy(mati, UP);
        topic.refresh();

        assertThat(topic.votes.size(), is(1));
        assertThat(mati.votes.size(), is(1));
        assertThat(topic.score, is(1));
    }

    @Test
    public void voteDown() {
        topic.votedBy(mati, DOWN);
        topic.refresh();

        assertThat(topic.votes.size(), is(1));
        assertThat(mati.votes.size(), is(1));
        assertThat(topic.score, is(-1));
    }

    @Test
    public void multipleVotesInSameDirection() {
        topic.votedBy(mati, UP);
        topic.votedBy(mati, UP);
        topic.refresh();

        assertThat(topic.score, is(1));
    }

    @Test
    public void voteUpAndDown() {
        topic.votedBy(mati, UP);
        topic.votedBy(mati, DOWN);
        topic.refresh();

        assertThat(topic.votes.size(), is(0));
        assertThat(topic.score, is(0));
    }

    @Test
    public void multipleUsersVoteUp() {
        topic.votedBy(mati, UP);
        topic.votedBy(mihkel, UP);
        topic.refresh();

        assertThat(topic.votes.size(), is(2));
        assertThat(topic.score, is(2));
    }

    @Test
    public void multipleUsersVoteUpAndDown() {
        topic.votedBy(mati, UP);
        topic.votedBy(mihkel, DOWN);
        topic.refresh();

        assertThat(topic.votes.size(), is(2));
        assertThat(topic.score, is(0));
    }
}
