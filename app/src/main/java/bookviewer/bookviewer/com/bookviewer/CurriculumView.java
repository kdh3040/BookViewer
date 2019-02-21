package bookviewer.bookviewer.com.bookviewer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;

import bookviewer.bookviewer.com.bookviewer.Curriculum.Curriculum_first;
import bookviewer.bookviewer.com.bookviewer.Curriculum.Curriculum_fourth;
import bookviewer.bookviewer.com.bookviewer.Curriculum.Curriculum_second;
import bookviewer.bookviewer.com.bookviewer.Curriculum.Curriculum_third;
import bookviewer.bookviewer.com.bookviewer.Data.DataMgr;


public class CurriculumView extends Fragment {

    ViewPager viewPager;
    private TabLayout tabLayout;
    View fragView;


    public CurriculumView() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (fragView!= null) {

        }
        else
        {
            // Inflate the layout for this fragment
            fragView = inflater.inflate(R.layout.fragment_curriculum_view,container,false);

            tabLayout = fragView.findViewById(R.id.tabLayout);

            for(int i = 0; i < DataMgr.getInstance().myData.curriculumData.size(); i++)
            {
                tabLayout.addTab(tabLayout.newTab().setText(DataMgr.getInstance().myData.curriculumData.get(i).curriculumName));
            }

            viewPager = (ViewPager)fragView.findViewById(R.id.vp);
            //viewPager.setOffscreenPageLimit(3);

            viewPager.setAdapter(new TabPagerAdapter(getFragmentManager(),tabLayout.getTabCount()));
            viewPager.setPageTransformer(true, new RotateDownTransformer());
            viewPager.setCurrentItem(0);
            viewPager.setOffscreenPageLimit(4);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }


        return fragView;
    }

private class TabPagerAdapter extends FragmentStatePagerAdapter {
    private  int tabCount;
    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount =tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return new Curriculum_first();
            case 1:
                return new Curriculum_first();
            case 2:
                return new Curriculum_first();
            case 3:
                return new Curriculum_first();
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

}
