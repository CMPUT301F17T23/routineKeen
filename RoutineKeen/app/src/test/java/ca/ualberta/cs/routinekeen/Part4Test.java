package ca.ualberta.cs.routinekeen;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

import ca.ualberta.cs.routinekeen.Views.LoginActivity;

/**
 * Created by aridgway120 on 2017-11-13.
 */

public class Part4Test extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public Part4Test() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testLogin() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

    }
}
