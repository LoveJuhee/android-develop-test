package com.kit.developtest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.FadeExit.FadeExit;
import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.kit.developtest.fragments.BeaconFragment;
import com.kit.developtest.fragments.GoogleMapFragment;
import com.kit.developtest.fragments.RestFulVolleyFragment;
import com.kit.developtest.fragments.RestFulVolleyFragment.OnFragmentInteractionListener;
import com.kit.developtest.fragments.thridparty.ButterKnifeFragment;
import com.kit.developtest.fragments.thridparty.FlycoDialogFragment;
import com.kit.developtest.fragments.thridparty.NiftyDialogEffectsFragment;
import com.kit.developtest.utils.ToastUtil;
import com.navercorp.volleyextensions.volleyer.Volleyer;
import com.navercorp.volleyextensions.volleyer.factory.DefaultRequestQueueFactory;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener,
    NavigationView.OnNavigationItemSelectedListener, BeaconFragment.OnFragmentInteractionListener,
    ButterKnifeFragment.OnFragmentInteractionListener,
    GoogleMapFragment.OnFragmentInteractionListener,
    NiftyDialogEffectsFragment.OnFragmentInteractionListener,
    FlycoDialogFragment.OnFragmentInteractionListener {

  private static final String TAG = MainActivity.class.getSimpleName();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                                                             R.string.navigation_drawer_open,
                                                             R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    /**
     * Volleyer setting
     */
    RequestQueue rq = DefaultRequestQueueFactory.create(this);
    rq.start();
    Volleyer.volleyer(rq).settings().setAsDefault().done();
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
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

  @SuppressWarnings("StatementWithEmptyBody") @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    Fragment fragment = null;
    if (id == R.id.nav_rest_ful_volley) {
      // Handle the camera action
      fragment = RestFulVolleyFragment.newInstance();
    } else if (id == R.id.nav_beacon_reco) {
      fragment = BeaconFragment.newInstance();
    } else if (id == R.id.nav_butter_knife) {
      fragment = ButterKnifeFragment.newInstance();
    } else if (id == R.id.nav_google_map) {
      Fragment current = getFragmentManager().findFragmentByTag(TAG);
      if (current instanceof GoogleMapFragment == false) {
        fragment = GoogleMapFragment.newInstance();
      }
    } else if (id == R.id.nav_nifty_dialog_effects) {
      onNiftyDialogTest();
      fragment = NiftyDialogEffectsFragment.newInstance();
    } else if (id == R.id.nav_flyco_dialog) {
      onFlycoDialogTest();
      fragment = FlycoDialogFragment.newInstance();
    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    if (fragment != null) {
      FragmentManager manager = getFragmentManager();
      manager.beginTransaction().replace(R.id.content, fragment, TAG).commit();
    } else {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }


  BaseAnimatorSet bas_in = new FlipVerticalSwingEnter();
  BaseAnimatorSet bas_out = new FadeExit();
  Context context = null;

  private void onFlycoDialogTest() {
    context = this;
    final NormalDialog dialog = new NormalDialog(context);
    dialog.content("let's go?")//
        .showAnim(bas_in)//
        .dismissAnim(bas_out)//
        .show();

    dialog.setOnBtnClickL(new OnBtnClickL() {
      @Override public void onBtnClick() {
        ToastUtil.showShortToast(context, true, "left");
        dialog.dismiss();
      }
    }, new OnBtnClickL() {
      @Override public void onBtnClick() {
        ToastUtil.showShortToast(context, true, "right");
        dialog.dismiss();
      }
    });
  }

  void onNiftyDialogTest() {
    context = this;
    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);

    dialogBuilder
        .withTitle("Modal Dialog")
        .withMessage("This is a modal Dialog.")
        .show();
  }

  public void onFragmentInteraction(Uri uri) {
  }
}
