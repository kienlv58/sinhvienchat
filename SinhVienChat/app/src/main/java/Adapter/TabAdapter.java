package Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import TabFragment.Tab1Fragment;
import TabFragment.Tab2Fragment;
import TabFragment.Tab3Fragment;
import TabFragment.Tab4Fragment;

/**
 * Created by Kiên on 3/11/2016.
 */
public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Tab4Fragment.neInstance();
            case 1:
                return Tab1Fragment.neInstance();
            case 2:
                return Tab2Fragment.neInstance();
            case 3:
                return Tab3Fragment.neInstance();
            default:
                return Tab4Fragment.neInstance();

        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:

                return "Profile";
            case 1:
                return "Chat";
            case 2:
                return "New";
            case 3:
                return "Author";
        }
        return super.getPageTitle(position);
    }
}
