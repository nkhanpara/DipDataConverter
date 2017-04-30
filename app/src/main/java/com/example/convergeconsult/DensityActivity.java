package com.example.convergeconsult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class DensityActivity extends AppCompatActivity {
    private AdView mAdView;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsDensity);
        tabLayout.setupWithViewPager(mViewPager);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5276594353990169~2024613538");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView1= (TextView) rootView.findViewById(R.id.textView1);
            textView1.setText(R.string.convDensity);
            TextView textView2= (TextView) rootView.findViewById(R.id.textView2);
            textView2.setText(R.string.obvDensity);
            TextView textView3= (TextView) rootView.findViewById(R.id.textView3);
            textView3.setText(R.string.temperature);

            final EditText reading1EditText= (EditText) rootView.findViewById(R.id.reading1EditText);
            reading1EditText.setHint(R.string.editText1Density);
            final EditText reading2EditText= (EditText) rootView.findViewById(R.id.reading2editText);
            reading2EditText.setHint(R.string.editText2Density);

            final TextView textViewanswer = (TextView) rootView.findViewById(R.id.answer);

            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(reading1EditText, InputMethodManager.SHOW_IMPLICIT);
            imm.showSoftInput(reading2EditText, InputMethodManager.SHOW_IMPLICIT);
            reading1EditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    textViewanswer.setText("NA");
                    String editText1String = reading1EditText.getText().toString().trim();
                    String editText2String = reading2EditText.getText().toString().trim();
                    if(!(editText1String.isEmpty() || editText2String.isEmpty())){
                        double temperature = Double.parseDouble(editText2String);
                        int indexI = (int) (temperature * 2);

                        if(editText1String.contains("."))
                            return;
                        int obvDen = Integer.parseInt(editText1String);
                        int indexJ = getNormalizedIndex(obvDen);
                        if(validateInput(indexI,indexJ)) {
                            String ans = DBDefaultValues.getDensitydata(indexI,indexJ,getArguments().getInt(ARG_SECTION_NUMBER));
                            textViewanswer.setText(ans);
                        }
                    }
                }
                public int getNormalizedIndex(int indexJ){
                    switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                        case 1:
                            return indexJ - 700;
                        case 2:
                            return indexJ - 800;
                        default:
                            return -1;
                    }
                }
                public boolean validateInput(int i, int j){
                    if(i >= 0 && i <= 100 && j >= 0 && j <= 59)
                        return true;
                    else
                        return false;
                }
            });
            reading2EditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    textViewanswer.setText("NA");
                    String editText1String = reading1EditText.getText().toString().trim();
                    String editText2String = reading2EditText.getText().toString().trim();
                    if(!(editText1String.isEmpty() || editText2String.isEmpty())){
                        double temperature = Double.parseDouble(editText2String);
                        int indexI = (int) (temperature * 2);

                        if(editText1String.contains("."))
                            return;
                        int obvDen = Integer.parseInt(editText1String);
                        int indexJ = getNormalizedIndex(obvDen);
                        if(validateInput(indexI,indexJ)) {
                            String ans = DBDefaultValues.getDensitydata(indexI,indexJ,getArguments().getInt(ARG_SECTION_NUMBER));
                            textViewanswer.setText(ans);
                        }
                    }
                }
                public int getNormalizedIndex(int indexJ){
                    switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                        case 1:
                            return indexJ - 700;
                        case 2:
                            return indexJ - 800;
                        default:
                            return -1;
                    }
                }
                public boolean validateInput(int i, int j){
                    if(i >= 0 && i <= 100 && j >= 0 && j <= 59)
                        return true;
                    else
                        return false;
                }
            });
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
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Petrol";
                case 1:
                    return "Diesel";
            }
            return null;
        }
    }
}
