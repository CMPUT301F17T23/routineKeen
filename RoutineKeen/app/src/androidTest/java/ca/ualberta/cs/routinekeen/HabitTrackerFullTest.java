package ca.ualberta.cs.routinekeen;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import ca.ualberta.cs.routinekeen.Views.HabitEditActivity;
import ca.ualberta.cs.routinekeen.Views.HabitHistoryActivity;
import ca.ualberta.cs.routinekeen.Views.HabitListActivity;
import ca.ualberta.cs.routinekeen.Views.LoginActivity;
import ca.ualberta.cs.routinekeen.Views.NewHabitActivity;
import ca.ualberta.cs.routinekeen.Views.UserMenu;

/**
 * Created by aridgway120 on 2017-12-03.
 */

public class HabitTrackerFullTest extends ActivityInstrumentationTestCase2 {
    private static final String LOG = "SoloTestTag";
    private static final String TEST_USER_NAME = "Test User Dec 2017";
    private static final String TEST_HABIT_NAME = "Practice Juggling";
    private static final String TEST_HABIT_REASON = "Become a clown";
    private static final String TEST_HABIT_REASON2 = "Become something else";
    private Solo solo;
    private int userIndex;

    public HabitTrackerFullTest() {
        super(ca.ualberta.cs.routinekeen.Views.LoginActivity.class);
    }

    public void setUp() throws Exception {
        Solo.Config c = new Solo.Config();
        c.commandLogging=true;
        solo = new Solo(getInstrumentation(), c, getActivity());
    }

    public void addTestUser() {

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        // add new user
        solo.clickOnButton("Add New Profile");
        solo.enterText((EditText) solo.getView(R.id.login_edit_profileName), TEST_USER_NAME);
        solo.clickOnButton("Add Profile");
    }

    public void login() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        ListView loginLv = (ListView) solo.getView("login_listview_userSelect");
        userIndex = loginLv.getAdapter().getCount();
        solo.clickInList(userIndex);
    }


    /**
     * Adds a new habit, saves it, then edits its habit reason and schedule and saves again
     */

    @Test
    public void testHabitList() {

        login();

        // USER MENU
        solo.assertCurrentActivity("Wrong Activity", UserMenu.class);
        // open habit list
        solo.clickOnText("View Habit List");

        // HABIT LIST
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        // click on add button
        solo.clickOnImageButton(0);

        // NEW HABIT
        solo.assertCurrentActivity("Wrong Activity", NewHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.addHabit_editText_name), TEST_HABIT_NAME);
        solo.enterText((EditText) solo.getView(R.id.addHabit_editText_reason), TEST_HABIT_REASON);
        solo.clickOnButton("Pick Start Date");
        solo.setDatePicker(0, 2017, 11, 1);
        solo.clickOnButton("OK");

        solo.clickOnButton("Add");

        // HABIT LIST
        solo.waitForText(TEST_HABIT_NAME);

        solo.clickInList(0);

        // HABIT EDIT
        solo.assertCurrentActivity("Wrong Activity", HabitEditActivity.class);
        // clear and change habit reason
        solo.clearEditText((EditText) solo.getView(R.id.editHabit_habitReasonField));
        solo.enterText((EditText) solo.getView(R.id.editHabit_habitReasonField), TEST_HABIT_REASON2);
        // schedule habit for weekdays only
        solo.clickOnText("Sat");
        solo.clickOnText("Sun");
        // save
        solo.clickOnButton("Save");
    }

    @Test
    public void testHabitHistory() {

        login();

        // USER MENU
        solo.assertCurrentActivity("Wrong Activity", UserMenu.class);
        solo.clickOnText("View Habit History");

        // HABIT HISTORY
        solo.assertCurrentActivity("Wrong Activity", HabitHistoryActivity.class);
        solo.clickOnButton("Add Habit Event");
    }
}
