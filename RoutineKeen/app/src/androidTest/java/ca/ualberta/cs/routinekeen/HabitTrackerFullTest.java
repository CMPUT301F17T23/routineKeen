package ca.ualberta.cs.routinekeen;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Test;

import ca.ualberta.cs.routinekeen.Views.LoginActivity;

/**
 * Created by aridgway120 on 2017-12-03.
 */

public class HabitTrackerFullTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public HabitTrackerFullTest() {
        super(ca.ualberta.cs.routinekeen.Views.LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void testLogin() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        try {
            solo.clickInList(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
