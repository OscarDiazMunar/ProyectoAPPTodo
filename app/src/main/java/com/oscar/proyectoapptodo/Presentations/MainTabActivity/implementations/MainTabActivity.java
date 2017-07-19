package com.oscar.proyectoapptodo.Presentations.MainTabActivity.implementations;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.implementations.ClimaDialogFragment;
import com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.youtubeFragment.implementations.YoutubeFragment;
import com.oscar.proyectoapptodo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainTabActivity extends AppCompatActivity {

    @Bind(R.id.imgYoutube)
    ImageView imgYoutube;
    @Bind(R.id.imgCamera)
    ImageView imgCamera;
    @Bind(R.id.imgPhone)
    ImageView imgPhone;
    @Bind(R.id.fabtoolbar)
    FABToolbarLayout fabtoolbar;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private YoutubeFragment youtubeFragment;
    private FragmentTransaction transaction;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private MainTabPresenter mainTabPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabtoolbar.show();
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });


        mainTabPresenter = new MainTabPresenter(this);
        mainTabPresenter.onCreate();


    }

    @Override
    protected void onStop() {
        super.onStop();
        mainTabPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainTabPresenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        fabtoolbar.hide();
        youtubeFragment.onDestroyView();
        youtubeFragment.onDestroy();
        youtubeFragment.onDetach();
        transaction.remove(youtubeFragment);
        transaction.hide(youtubeFragment);
        //transaction.commit();
        fragmentContainer.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.closeSesion:
                mainTabPresenter.closeSession();
                return true;
            case R.id.clima:
                /*JsonObjectRequest jsonObjectRequest = VolleyManager.makeRequestJson("http://api.openweathermap.org/data/2.5/weather?q=medellin&appid=072ede38bdcd58804ca961ad3104bae8");
                AppController.getInstance().addToRequestQueue(jsonObjectRequest);*/

                /*StringRequest jsonObjectRequest = VolleyManager.makeRequestString("http://api.openweathermap.org/data/2.5/weather?q=medellin&appid=072ede38bdcd58804ca961ad3104bae8");
                AppController.getInstance().addToRequestQueue(jsonObjectRequest);*/

                /*mainTabPresenter.consumeWebService("http://api.openweathermap.org/data/2.5/weather?q=medellin&appid=072ede38bdcd58804ca961ad3104bae8",
                        Constants.typeResponseWebService.TYPE_XML);*/

                new ClimaDialogFragment().show(getFragmentManager(), "ClimaDialog");
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.imgYoutube, R.id.imgCamera, R.id.imgPhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgYoutube:
                cargarYoutube();
                break;
            case R.id.imgCamera:
                break;
            case R.id.imgPhone:
                break;
        }
    }

    private void cargarYoutube() {
        fragmentContainer.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        youtubeFragment = new YoutubeFragment();
        transaction.add(R.id.fragment_container, youtubeFragment);
        transaction.commit();

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_tab, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
