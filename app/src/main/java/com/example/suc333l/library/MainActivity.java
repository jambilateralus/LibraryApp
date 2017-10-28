package com.example.suc333l.library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {


    private class FragmentSwitcher {

        private FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        void switchToHomeFragment() {
            fragmentTransaction.replace(R.id.fragment, new HomeFragment());
            fragmentTransaction.commit();
        }

        void switchToBooksFragment() {
            fragmentTransaction.replace(R.id.fragment,new BooksFragment());
            fragmentTransaction.commit();
        }

        void switchToRequestBookFragment() {
            fragmentTransaction.replace(R.id.fragment, new RequestBookFragment());
            fragmentTransaction.commit();
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentSwitcher fragmentSwitcher = new FragmentSwitcher();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentSwitcher.switchToHomeFragment();
                    return true;

                case R.id.navigation_books:
                    fragmentSwitcher.switchToBooksFragment();
                    return true;

                case R.id.navigation_request_books:
                    fragmentSwitcher.switchToRequestBookFragment();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        new FragmentSwitcher().switchToHomeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mSettings: {
                // open settings
            }

            case R.id.mLogout: {
                // logout
                SharedPreferences preferences = getSharedPreferences(getString(R.string.login_data),
                        MODE_PRIVATE);
                preferences.edit().remove("token").apply();
                preferences.edit().remove("hasLoggedIn").apply();

                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

}
