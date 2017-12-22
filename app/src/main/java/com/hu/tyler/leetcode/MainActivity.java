package com.hu.tyler.leetcode;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    Toolbar toolbar;
    public static Context mainContext = null;
    //public static final String urlLink = "https://api.myjson.com/bins/lkpx7"; //link to fetch JSON file
    public static RecyclerView.Adapter adapter, DataBaseadapter, shellAdapter;
    public static List<ListItem> arr1, databaseQ, shellarray;
    public static int checker = 0; // ensure loaddata() doesn't happen more than once
    public String dataBaseFile = "DatabaseQuestions.json", algorithmnFile = "AlgorithmQuestions.json", shellFile = "ShellBaseQuestions.json";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this,"onDestroy has executed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainContext = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (checker == 0) {
            //loading database questions list and fragmentation
            databaseQ = loadJSONFromAsset(dataBaseFile);

            /// another adapter for recyclerView
            android.support.v4.app.FragmentTransaction fragmentTransaction1 =
                    getSupportFragmentManager().beginTransaction();
            DataBaseadapter = new TylerAdapter(databaseQ, getApplicationContext(), fragmentTransaction1);
            // r1View.setAdapter(adapter);


            ////loading algorithm list and fragmentation
            arr1 = loadJSONFromAsset(algorithmnFile);
            /// another adapter for recyclerView
            android.support.v4.app.FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            adapter = new TylerAdapter(arr1, getApplicationContext(), fragmentTransaction2);
            // r1View.setAdapter(adapter);


            ////loading Shell questions list and fragmentation
            shellarray = loadJSONFromAsset(shellFile);
            android.support.v4.app.FragmentTransaction fragmentTransaction3 =
                    getSupportFragmentManager().beginTransaction();
            shellAdapter = new TylerAdapter(shellarray, getApplicationContext(), fragmentTransaction3);

            checker++;
        }
        TextView title = (TextView) findViewById(R.id.MainTitle);
        title.setVisibility(View.VISIBLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //New Method to grab data from the assets folder
    public ArrayList<ListItem> loadJSONFromAsset(String filename) {
        ArrayList<ListItem> locList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            //return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("Questions");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject o = jsonArray.getJSONObject(i);
                ListItem x = new ListItem(o.getString("title"), o.getString("Difficulty"), o.getString("description"),
                        o.getString("example"), o.getString("solution"), o.getString("extra"));

                //ListItem constructor params - > (String title, String rank, String description, String example, String solution)
                locList.add(x);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locList;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        TextView textView = (TextView) findViewById(R.id.MainTitle);

        textView.setVisibility(View.INVISIBLE);
        if (id == R.id.algorithmList) {
            ///this is for the list of codes, the id "nav_camera" is default rom android studio
            CodeList fragment = new CodeList();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            //Passing variables into Fragments
            // fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, 0,0);
            Bundle bundle = new Bundle();

            fragment.setArguments(bundle);
            bundle.putString("title", "Algorthmn Base Questions");
            fragmentTransaction.replace(R.id.fragmentHolder, fragment);
            fragmentTransaction.addToBackStack(null).commit();

            // Handle the camera action
        } else if (id == R.id.randomQuestion) {
            ///this is for a randomly generated question, the id "nav_gallery" is default rom android studio
            DetailQuestion fragment2 = new DetailQuestion();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            int min = 0, max = arr1.size();
            Log.d("XXX", " Right before crash size of arr1: " + arr1.size());
            Random r = new Random();
            int x1 = r.nextInt(max - min); // should generate number between 0 and arr1.size-1
            String g6 = arr1.get(x1).getTitle();
            Bundle bundle = new Bundle();
            bundle.putString("description", arr1.get(x1).getDescription());
            bundle.putString("solution", arr1.get(x1).getSolution());
            bundle.putString("title", arr1.get(x1).getTitle());
            bundle.putString("example", arr1.get(x1).getExample());

            fragment2.setArguments(bundle);

            fragmentTransaction.replace(R.id.fragmentHolder, fragment2);
            fragmentTransaction.addToBackStack(null).commit();


        } else if (id == R.id.dataBaseQuestions) {
            DatabaseList fragment = new DatabaseList();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragmentHolder, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        } else if (id == R.id.shellQuestion) {
            ShellQuestions fragment = new ShellQuestions();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragmentHolder, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        } else if (id == R.id.about) {
            About fragment = new About();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            //Passing variables into Fragments

            fragmentTransaction.replace(R.id.fragmentHolder, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        }
          /*  Toast.makeText(this, "Tools Under Construction", Toast.LENGTH_SHORT).show();
         else if (id == R.id.nav_share) {
            Toast.makeText(this, "Share Under Construction", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Send Under Construction", Toast.LENGTH_SHORT).show();

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



////////////////////////////////////////////////////////////////////////////////////////////////
/// This belongs in onCreate method.
       /*
        This is a below sets the action for the floating mail icon which I wont need, thus
        it shall be commented out.
        *  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        *
        * */

////////////////////////////////////////////////////////////////////////////////////////////////
/* this is only for getting data from the internet which has now been updated as of 7/23/17 to be local; thus offline access is possible
   I'm leaving this here for future revisions.
    private void loadData()
    {
        Log.v("XXX", "Entered Load Data." );
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Loading data...");
        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlLink,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        loading.dismiss();
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("Questions");
                            for(int i = 0; i < array.length(); i++)
                            {
                                JSONObject o = array.getJSONObject(i);
                                ListItem x = new ListItem(i+1 + ". "+ o.getString("title" ), o.getString("Difficulty" ), o.getString("description"),
                                        o.getString("example"), o.getString("solution"), o.getString("extra"));

                                //ListItem constructor params - > (String title, String rank, String description, String example, String solution)
                                arr1.add(x);
                            }

                          //  android.support.v4.app.FragmentTransaction fragmentTransaction =
                            //        getSupportFragmentManager().beginTransaction();
                             android.support.v4.app.FragmentTransaction fragmentTransaction0 =
                                    getSupportFragmentManager().beginTransaction();
                            adapter = new TylerAdapter(arr1, getApplicationContext(), fragmentTransaction0);
                            // r1View.setAdapter(adapter);
                            Log.v("XXX", "Seems that file was gotten successful." );
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    */
////////////////////////////////////////////////////////////////////////////////////////////////