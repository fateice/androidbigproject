package hit.edu.cn.buscoming.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

<<<<<<< HEAD
=======

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import hit.edu.cn.buscoming.Adapter.RecentArrayAdapter;

>>>>>>> 621640fb2afdca8afa9d7322baa4f5fe7d04a2d9
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


import hit.edu.cn.buscoming.Base.BaseActivity;
import hit.edu.cn.buscoming.Collector.ActivityCollector;
import hit.edu.cn.buscoming.Config;
import hit.edu.cn.buscoming.R;
import hit.edu.cn.buscoming.Wea.Weather;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View test = findViewById(R.id.test);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




//        未登录
       if(sgetname().equals("unknown")){
           TextView mytext = (TextView)findViewById(R.id.mytext);
           mytext.setText("未登录");


        }
        else
       {

           ListView mylist = (ListView)findViewById(R.id.homebiglist);
           TextView mytext = (TextView)findViewById(R.id.mytext);
           DBManager db = new DBManager(MainActivity.this);
           final List<Recent> r=db.getRecent(sgetname(),1,db.getnum("recent",1));
           int b= db.getnum("recent",2);
           int a=0;
           while(a<b)
           {
               r.add(db.getRecent(sgetname(),2,b).get(a));
               a++;
           }
           b= db.getnum("recent",3);
           a=0;
           while(a<b)
           {
               r.add(db.getRecent(sgetname(),3,b).get(a));
               a++;
           }



           RecentArrayAdapter _adapter = new RecentArrayAdapter(MainActivity.this,R.layout.list_item,r);
           mylist.setAdapter(_adapter);
           mytext.setText("最近查询");
       }



        final Config key = (Config) getApplication();

        final TextView main_city = (TextView) findViewById(R.id.main_city);
        final TextView main_time = (TextView) findViewById(R.id.main_time);
        final TextView now_temp = (TextView) findViewById(R.id.now_temp);
        final TextView temprange = (TextView) findViewById(R.id.temprange);
        final TextView weatext = (TextView) findViewById(R.id.weatext);
        final TextView update_time = (TextView) findViewById(R.id.update_time);

        new Thread() {
            public void run() {
                String city = "";
                if ("unknown".equals(sgetcity())) {
                    city = "哈尔滨";
                } else {
                    city = sgetcity();
                }
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest jsonObjectRequest = new StringRequest(

                        Request.Method.GET, "http://v.juhe.cn/weather/index?format=2&cityname=" + city + "&key=" + "d5da63fc4ddc5a9398bed8867089f1ec",

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Log.d("TAGG", response.toString());
                                Gson gson = new Gson();
                                Weather weather;
                                weather = gson.fromJson(response, Weather.class);


                                main_city.setText(weather.result.today.getCity());
                                main_time.setText(weather.result.today.getDate_y());
                                now_temp.setText(weather.result.sk.getTemp());
                                temprange.setText(weather.result.today.getTemperature());
                                weatext.setText(weather.result.today.getWeather());
                                update_time.setText("已更新 " + weather.result.sk.getTime());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAGG", error.toString());
                    }
                }
                );
                requestQueue.add(jsonObjectRequest);

            }

        }.start();


    }





    public void fabline (View view)
    {
        String city="";
        if ("unknown".equals(sgetcity()))
        {
            city="";
        }
        else
        {
            city = sgetcity();
        }
        Intent intent = new Intent();
        intent.putExtra("extra",city);
        intent.setClass(MainActivity.this,LineActivity.class);
        startActivity(intent);
    }

    public void fabstop (View view)
    {
        String city="";
        if ("unknown".equals(sgetcity()))
        {
            city="";
        }
        else
        {
            city = sgetcity();
        }
        Intent intent = new Intent();
        intent.putExtra("extra",city);
        intent.setClass(MainActivity.this,StationActivity.class);
        startActivity(intent);
    }

    public void fabdes (View view)
    {
        String city = "";
        if ("unknown".equals(sgetcity()))
        {
            city="";
        }
        else
        {
            city = sgetcity();
        }
        Intent intent = new Intent();
        intent.putExtra("extra",city);
        intent.setClass(MainActivity.this,TransferActivity.class);
        startActivity(intent);
    }

    //保存当前登录的用户的用户名和偏好城市
    public void ssave(String user,String city){
        SharedPreferences sharedPreferences = getSharedPreferences("ussss", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user",user);
        editor.putString("city",city);
        editor.commit();
    }

    //当前登录的用户名
    public String sgetname(){
        SharedPreferences sharedPreferences = getSharedPreferences("ussss",Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("user","unknown");
        return name;
    }

    //当前选择的城市
    public String sgetcity(){
        SharedPreferences sharedPreferences = getSharedPreferences("ussss",Context.MODE_PRIVATE);
        String city = sharedPreferences.getString("city","unknown");
        return city;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if(!sgetname().equals("unknown")){
            TextView textpls = (TextView)findViewById(R.id.textViewpls);
            TextView textname = (TextView)findViewById(R.id.textEmail);
            textname.setText(sgetname());
            textpls.setText(sgetcity());
        }
        if(sgetname().equals("unknown")){
            TextView textpls = (TextView)findViewById(R.id.textViewpls);
            TextView textname = (TextView)findViewById(R.id.textEmail);
            textname.setText("未登录");
            textpls.setText("请点击按钮登录");
        }
        Log.d("Create Menu", "Created");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("Created", "Menu  Created");
        /*
        TextView textEmail = (TextView)findViewById(R.id.textEmail);
        if("unknown".equals(sgetname())) {
            textEmail.setText("unknow");
        } else {
            textEmail.setText(sgetname());
        }
        */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle action bar activity_city_select_item clicks here. The action bar will
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
        // Handle navigation view activity_city_select_item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // 登录
        } else if (id == R.id.nav_login) {
            if(sgetname().equals("unknown")) {
                Intent nav_login_intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(nav_login_intent);
            } else {
                Toast.makeText(MainActivity.this, "你已登录成功，无需重复登录", Toast.LENGTH_SHORT).show();
            }
            // 选择城市
        } else if (id == R.id.nav_city) {
            Intent nav_select_city_intent = new Intent(MainActivity.this, CitySelectActivity.class);
            startActivity(nav_select_city_intent);
            // 关于
        } else if(id == R.id.nav_about) {
            Intent nav_about_intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(nav_about_intent);
            // 注销
        }else if(id == R.id.nav_logout) {
            if(sgetname().equals("unknown")) {
                Toast.makeText(MainActivity.this, "你还没有登录，无法注销", Toast.LENGTH_SHORT).show();
            } else {
                ssave("unknown","unknown");
                Toast.makeText(this, "注销成功", Toast.LENGTH_SHORT).show();
            }
            // 退出
        }else if (id == R.id.nav_exit) {
            Log.d("Now", "exit");
            Toast.makeText(MainActivity.this, "退出成功,别忘了日后打开吆~", Toast.LENGTH_SHORT).show();
            ActivityCollector.finishAll();
        }else if(id == R.id.nav_offten) {
            if (sgetname().equals("unknown"))
            {
                Toast.makeText(this, "未登录，请先登录再查看常去", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent nav_offten_intent = new Intent(MainActivity.this, RecentActivity.class);
                startActivity(nav_offten_intent);
            }
        }else if (id == R.id.nav_star){
            if (sgetname().equals("unknown"))
            {
                Toast.makeText(this, "未登录，请先登录再查看收藏", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent nav_star_intent = new Intent(MainActivity.this, StarActivity.class);
                startActivity(nav_star_intent);

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
