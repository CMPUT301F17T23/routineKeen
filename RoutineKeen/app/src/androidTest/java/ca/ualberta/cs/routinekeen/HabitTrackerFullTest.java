package ca.ualberta.cs.routinekeen;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Test;

import ca.ualberta.cs.routinekeen.Views.LoginActivity;
import ca.ualberta.cs.routinekeen.Views.UserMenu;

/**
 * Created by aridgway120 on 2017-12-03.
 */

public class HabitTrackerFullTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public HabitTrackerFullTest() {
        super(ca.ualberta.cs.routinekeen.Views.LoginActivity.class);
    }

    public void setUp() throws Exception {
        Solo.Config c = new Solo.Config();
        c.commandLogging=true;
        solo = new Solo(getInstrumentation(), c, getActivity());
    }

    @Test
    public void testLogin() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        try {
            solo.clickInList(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        solo.assertCurrentActivity("Wrong Activity", UserMenu.class);
    }
}
