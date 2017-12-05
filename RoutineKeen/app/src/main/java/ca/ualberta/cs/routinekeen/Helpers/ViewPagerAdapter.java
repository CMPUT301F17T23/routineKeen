package ca.ualberta.cs.routinekeen.Helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * A simple helper class used as a custom adapter for viewing fragments in HabitFollowingSharing Activity
 *
 * @author Mikee V
 * @see ca.ualberta.cs.routinekeen.Views.HabitFollowingSharing
 * @version 1.0.0
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    //Constructor
    public void addFragments(Fragment fragments, String title)
    {
        this.fragments.add(fragments);
        this.tabTitles.add(title);
    }


    public ViewPagerAdapter(FragmentManager fragment)
    {
        super(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles.get(position);
    }
}
