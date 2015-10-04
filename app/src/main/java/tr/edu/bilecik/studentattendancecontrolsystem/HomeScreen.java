package tr.edu.bilecik.studentattendancecontrolsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tr.edu.bilecik.studentattendancecontrolsystem.CustomClasses.MyActivityWithoutStatusBar;
import tr.edu.bilecik.studentattendancecontrolsystem.Fragments.AddDevicesFragment;
import tr.edu.bilecik.studentattendancecontrolsystem.Fragments.AttendanceControlFragment;
import tr.edu.bilecik.studentattendancecontrolsystem.Fragments.HomeFragment;
import tr.edu.bilecik.studentattendancecontrolsystem.Fragments.MyLessonsFragment;

public class HomeScreen extends MyActivityWithoutStatusBar {

    //Defining Variables
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;

    @Bind(R.id.navProfileImage)
    BootstrapCircleThumbnail navProfilePicture;

    @Bind(R.id.navUserName)
    TextView navStudentName;

    @Bind(R.id.navDepartment)
    TextView navStudentDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);

        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(toolbar);

        navStudentName.setText(ParseUser.getCurrentUser().getString("Name")+" "+ParseUser.getCurrentUser().getString("Surname"));
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Department");
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().get("Department"));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> departmentList, ParseException e) {
                if (e == null) {
                    navStudentDepartment.setText(departmentList.get(0).getString("DepartmentName"));
                } else {
                    Log.d("Department", "Error: " + e.getMessage());
                }
            }
        });

        MenuItem item = navigationView.getMenu().getItem(3);
        item.setVisible(false);

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Auth");
        query2.whereEqualTo("objectId", ParseUser.getCurrentUser().get("Auth"));
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.get(0).getString("AuthName").equals("Öğretim Görevlisi")) {
                    Menu menu = navigationView.getMenu();
                    menu.getItem(2).setVisible(true);
                    menu.getItem(3).setVisible(true);
                } else {
                    Menu menu = navigationView.getMenu();
                    menu.getItem(2).setVisible(false);
                    menu.getItem(3).setVisible(false);
                }
            }
        });


        //test data
        //navStudentName.setText("Gurkan Mustafa CAKIR");
        //navStudentDepartment.setText("Bilgisayar Mühendisligi");
        //navProfilePicture.setImage(R.drawable.background_material);


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                menuItem.setChecked(menuItem.isChecked() ? false : true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    //Replacing the main content with ContentFragment
                    case R.id.navHome:
                        Toast.makeText(getApplicationContext(), "d1 Selected", Toast.LENGTH_SHORT).show();
                        HomeFragment fragmentHome = new HomeFragment();
                        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionHome.replace(R.id.frame, fragmentHome);
                        fragmentTransactionHome.commit();
                        return true;
                    case R.id.navMyLessons:
                        Toast.makeText(getApplicationContext(), "d2 Selected", Toast.LENGTH_SHORT).show();
                        MyLessonsFragment fragmentMyLessons = new MyLessonsFragment();
                        FragmentTransaction fragmentTransactionMyLessons = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionMyLessons.replace(R.id.frame, fragmentMyLessons);
                        fragmentTransactionMyLessons.commit();
                        return true;
                    case R.id.navAttendance:
                        Toast.makeText(getApplicationContext(), "d3 Selected", Toast.LENGTH_SHORT).show();
                        AttendanceControlFragment fragmentAttendanceControl = new AttendanceControlFragment();
                        FragmentTransaction fragmentTransactionAttendanceControl = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAttendanceControl.replace(R.id.frame, fragmentAttendanceControl);
                        fragmentTransactionAttendanceControl.commit();
                        return true;
                    case R.id.navAddDevices:
                        Toast.makeText(getApplicationContext(), "d2 Selected", Toast.LENGTH_SHORT).show();
                        AddDevicesFragment fragmentAddDevices = new AddDevicesFragment();
                        FragmentTransaction fragmentTransactionAddDevices = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAddDevices.replace(R.id.frame, fragmentAddDevices);
                        fragmentTransactionAddDevices.commit();
                        return true;
                    case R.id.navLogout:
                        ParseUser.logOut();// çıkış yapılıp intent ile giriş sayfasına yönlendirme
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navSettings:
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "d6 Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}