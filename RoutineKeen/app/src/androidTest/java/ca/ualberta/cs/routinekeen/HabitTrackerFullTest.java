package ca.ualberta.cs.routinekeen;

import android.Manifest;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import ca.ualberta.cs.routinekeen.Views.AddHabitEvent;
import ca.ualberta.cs.routinekeen.Views.HabitEditActivity;
import ca.ualberta.cs.routinekeen.Views.HabitHistoryActivity;
import ca.ualberta.cs.routinekeen.Views.HabitHistoryFilterActivity;
import ca.ualberta.cs.routinekeen.Views.HabitListActivity;
import ca.ualberta.cs.routinekeen.Views.LoginActivity;
import ca.ualberta.cs.routinekeen.Views.NewHabitActivity;
import ca.ualberta.cs.routinekeen.Views.UserMenu;
import ca.ualberta.cs.routinekeen.Views.ViewHabitEvent;

/**
 * Created by aridgway120 on 2017-12-03.
 */

public class HabitTrackerFullTest extends ActivityInstrumentationTestCase2 {
    private static final String LOG = "SoloTestTag";
    private static final String TEST_USER_NAME = "Test User Fa17";
    private static final String TEST_HABIT_NAME = "Practice Juggling";
    private static final String TEST_HABIT_NAME2 = "Learn to Unicycle";
    private static final String TEST_HABIT_NAME3 = "Make balloon animals";
    private static final String TEST_HABIT_REASON = "Become a clown";
    private static final String TEST_HABIT_REASON2 = "Become something else";

    private static final String TEST_HABIT_EVENT_TITLE = "Juggled for 15 min";
    private static final String TEST_HABIT_EVENT_TITLE2 = "Unicycled for 20 min";
    private static final String TEST_HABIT_EVENT_COMMENT = "CommentFilterFlag";

    private Solo solo;
    private int userIndex;

    public HabitTrackerFullTest() {
        super(ca.ualberta.cs.routinekeen.Views.LoginActivity.class);
    }

    public void setUp() throws Exception {
        Solo.Config c = new Solo.Config();
        c.commandLogging = true;
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
        addTestUser();
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        ListView loginLv = (ListView) solo.getView("login_listview_userSelect");
        userIndex = loginLv.getAdapter().getCount();
        solo.clickInList(userIndex);
    }

    public void addTestHabit(String name, String reason) {
        // HABIT LIST
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        // click on add button
        solo.clickOnImageButton(0);

        // NEW HABIT
        solo.assertCurrentActivity("Wrong Activity", NewHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.addHabit_editText_name), name);
        solo.enterText((EditText) solo.getView(R.id.addHabit_editText_reason), reason);
        solo.clickOnButton("Pick Start Date");
        solo.setDatePicker(0, 2017, 11, 1);
        solo.clickOnButton("OK");

        solo.clickOnButton("Add");
    }

    public void addTestHabitEvent(String title, String comment, String type, Boolean includeLocation) {
        // HABIT HISTORY
        solo.assertCurrentActivity("Wrong Activity", HabitHistoryActivity.class);
        solo.clickOnButton("Add Habit Event");

        // ADD HABIT EVENT
        solo.assertCurrentActivity("Wrong Activity", AddHabitEvent.class);

        // set title & comment (if included)
        solo.enterText((EditText) solo.getView(R.id.eventTitle), title);
        if (comment != "") {
            solo.enterText((EditText) solo.getView(R.id.eventComment), comment);
        }
        if (!solo.isSpinnerTextSelected(type)) {
            solo.clickOnView(solo.getView(R.id.habitTypeSpinner));
            solo.clickOnText(type);
        }
        if (includeLocation) {
            solo.clickOnImageButton(R.id.imageButtonLocation);
        }
        solo.clickOnButton("Add");
    }

    public void filterHabitHistoryByComment(String comment) {
        // launch and check filter activity
        solo.assertCurrentActivity("Wrong Activity", HabitHistoryActivity.class);
        solo.clickOnButton("Filter List");
        solo.assertCurrentActivity("Wrong Activity", HabitHistoryFilterActivity.class);

        // enter filter comment
        solo.enterText((EditText) solo.getView(R.id.commentFilterText), comment);
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { Log.d(LOG, "wait interrupted"); }

        solo.clickOnView(solo.getView(R.id.applyFilterByCommentButton));
    }

    public void filterHabitHistoryByType(String type) {
        // launch and check filter activity
        solo.assertCurrentActivity("Wrong Activity", HabitHistoryActivity.class);
        solo.clickOnButton("Filter List");
        solo.assertCurrentActivity("Wrong Activity", HabitHistoryFilterActivity.class);

        // change habit type if needed
        Boolean b = solo.isSpinnerTextSelected(type);
        if (!b) {
            solo.clickOnView(solo.getView(R.id.typeFilterSpinner));
            solo.clickOnText(type);
        }

        solo.clickOnView(solo.getView(R.id.applyFilterByTypeButton));
    }

    public void clearHabitList() {
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);

        while (((ListView)solo.getView("listOfUserHabits")).getAdapter().getCount() > 0) {
            solo.clickInList(0);
            solo.assertCurrentActivity("Wrong Activity", HabitEditActivity.class);
            solo.clickOnButton("Delete");
        }
    }

    public void clearEventList() {
        solo.assertCurrentActivity("Wrong Activity", HabitHistoryActivity.class);

        while (((ListView)solo.getView("habitHistoryList")).getAdapter().getCount() > 0) {
            solo.clickInList(0);
            solo.assertCurrentActivity("Wrong Activity", ViewHabitEvent.class);
            solo.clickOnButton("Delete");
        }
    }

    public void disableAllDays() {
        solo.assertCurrentActivity("Wrong Activity", HabitEditActivity.class);
        solo.clickOnText("Mon");
        solo.clickOnText("Tue");
        solo.clickOnText("Wed");
        solo.clickOnText("Thu");
        solo.clickOnText("Fri");
        solo.clickOnText("Sat");
        solo.clickOnText("Sun");
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

        clearHabitList();

        addTestHabit(TEST_HABIT_NAME, TEST_HABIT_REASON);

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

        // delete habit
        clearHabitList();
    }

    @Test
    public void testHabitHistory() {

        login();
        solo.assertCurrentActivity("Wrong Activity", UserMenu.class);

        // add 2 habits
        solo.assertCurrentActivity("Wrong Activity", UserMenu.class);
        solo.clickOnText("View Habit List");
        clearHabitList();

        addTestHabit(TEST_HABIT_NAME, TEST_HABIT_REASON);
        addTestHabit(TEST_HABIT_NAME2, TEST_HABIT_REASON);
        solo.goBack();

        // add a variety of habit events
        solo.assertCurrentActivity("Wrong Activity", UserMenu.class);

        solo.clickOnText("View Habit History");
        clearEventList();

        addTestHabitEvent(TEST_HABIT_EVENT_TITLE, "", TEST_HABIT_NAME, false);
        addTestHabitEvent(TEST_HABIT_EVENT_TITLE, TEST_HABIT_EVENT_COMMENT, TEST_HABIT_NAME, false);
        addTestHabitEvent(TEST_HABIT_EVENT_TITLE2, TEST_HABIT_EVENT_COMMENT, TEST_HABIT_NAME2, false);

        solo.assertCurrentActivity("Wrong Activity", HabitHistoryActivity.class);

        // Test filter by comment
        filterHabitHistoryByComment(TEST_HABIT_EVENT_COMMENT);
        // check # of results (should be 2)
        Log.d(LOG, "check1");
        assertEquals(((ListView)solo.getView("habitHistoryList")).getAdapter().getCount(), 2);
        Log.d(LOG, "check2");

        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { Log.d(LOG, "wait interrupted"); }
        solo.clickOnButton("Clear Filter");


        // Test filter by habit type
        filterHabitHistoryByType(TEST_HABIT_NAME2);
        // check # of results (should be 1)
        assertEquals(((ListView)solo.getView("habitHistoryList")).getAdapter().getCount(), 1);

        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { Log.d(LOG, "wait interrupted"); }
        solo.clickOnButton("Clear Filter");


        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { Log.d(LOG, "wait interrupted"); }


        clearEventList();
        solo.goBack();
        solo.clickOnText("View Habit List");
        clearHabitList();
    }

    @Test
    public void testHabitSchedule() {

        // Get current day of the week in string form
        String today;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:   today = "Sun";  break;
            case Calendar.MONDAY:   today = "Mon";  break;
            case Calendar.TUESDAY:  today = "Tue";  break;
            case Calendar.WEDNESDAY:today = "Wed";  break;
            case Calendar.THURSDAY: today = "Thu";  break;
            case Calendar.FRIDAY:   today = "Fri";  break;
            case Calendar.SATURDAY: today = "Sat";  break;
            default:                today = null;   break;
        }

        login();

        // add 2 habits
        solo.assertCurrentActivity("Wrong Activity", UserMenu.class);
        solo.clickOnText("View Habit List");
        clearHabitList();

        addTestHabit(TEST_HABIT_NAME, TEST_HABIT_REASON);
        addTestHabit(TEST_HABIT_NAME2, TEST_HABIT_REASON);
        addTestHabit(TEST_HABIT_NAME3, TEST_HABIT_REASON);

        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickOnText(TEST_HABIT_NAME);
        disableAllDays();
        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickOnText(TEST_HABIT_NAME2);
        disableAllDays();
        solo.clickOnText(today);
        solo.clickOnButton("Save");

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", UserMenu.class);
        solo.clickOnText("View Habit Schedule");

        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { Log.d(LOG, "wait interrupted"); }
        // check that there are 2 habits in today's schedule
        assertEquals(((ListView)solo.getView("habitSchedule_listView")).getAdapter().getCount(), 2);
    }
}
