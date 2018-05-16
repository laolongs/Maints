package cn.tties.maint.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guider on 2018/1/3.
 * Email guider@yeah.net
 * github https://github.com/guider
 */

public class MainFragmentManager {
    private static MainFragmentManager instance;
    protected ArrayList<Fragment> fragments;
    protected FragmentManager fm;
    private int curentIndex = 0;
    private int lastIndex = 0;
    public static MainFragmentManager getInstance(FragmentManager fm, List<TabClass> array, int container) {
        if (instance == null)
            instance = new MainFragmentManager(fm, array, container);
        return instance;
    }
    public static MainFragmentManager getInstance() {
        return instance;
    }

    private MainFragmentManager(FragmentManager fm, List<TabClass> tabs, int containerId) {
        this.fm = fm;
        fragments = new ArrayList<>();
        init(tabs,containerId);
    }

    public void init(List<TabClass> tabs, int containId) {
        fragments.clear();
            for (TabClass tab : tabs){
                try {
                    fragments.add((Fragment) tab.getFragment().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : fragments) {
            if (f != null)
                ft.add(containId, f);
        }
        ft.commit();
    }


    public void showFragment(int position) {
        hideFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragments.get(position));
        ft.commit();
        lastIndex = curentIndex;
        curentIndex = position;
    }

    public void hideFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : fragments) {
            if (f != null) {
                ft.hide(f);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }


    public Fragment getCurrentFragment() {
        return fragments.get(curentIndex);
    }

    public Fragment getLastFragment() {
        return fragments.get(lastIndex);
    }


    public int getLastIndex() {
        return lastIndex;
    }

    public int getFragmentSize() {
        return fragments == null ? 0 : fragments.size();
    }

    public int getCurentIndex() {
        return curentIndex;
    }

    public void onDestory() {
        instance = null;
        curentIndex = 0;
        lastIndex = 0;
        fragments=null;
    }

}

