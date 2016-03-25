import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by singlasaahil on 2016-03-25.
 */
public class OneWordTest extends TestCase {

    OneWordTest onewordtest = new OneWordTest("What is your middle name");

    public void setUp() throws Exception {
        super.setUp();
        this.onewordtest.setCorrectAnswers("Kumar");

    }

    public void tearDown() throws Exception {

    }

    public void testGetCorrectAnswers() throws Exception {
        String expectedAnswer1 = "Kumar";
        String notExpectedAnswer1 = "Kaur";
        Set<String> actualAnswersSet = onewordtest.getCorrectAnswers();
        Set<String> expectedAnswersSet = new HashSet<String>() {{
            add("Kumar");
            add("Kaur");
        }};

        //test for the correct answer
        assertTrue("One Word Question does not have the expected correct answer.", actualAnswersSet.contains(expectedAnswer1));
        //test for option not present in the set
            assertFalse("One Word Question should not have this option as correct answer.", actualAnswersSet.contains(notExpectedAnswer1));
        //tests for multiple correct answers
        assertTrue("One Word Question does not have the expected correct answers.", actualAnswersSet.containsAll(expectedAnswersSet));
    }

    public void testGetInCorrectAnswers() throws Exception {

    }

    public void testGetUsersAnswer() throws Exception {

    }

    public void testSetCorrectAnswers() throws Exception {

    }

    public void testSetInCorrectAnswers() throws Exception {

    }

    public void testSetUsersAnswer() throws Exception {

    }
}