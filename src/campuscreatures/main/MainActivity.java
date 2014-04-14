package campuscreatures.main;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import profile.UserProfile;

import campuscreatures.location.LocationService;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

	private ViewPager viewPager;
	private MainPagerAdapter mAdapter;

	private Intent locationIntent = null;
	public static LocationService location = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		System.out.println("gere 2");
		if (!userProfileExists()) { //make sure a file called "userProfile has been created
			UserProfile templateProfile = new UserProfile("userProfile");
			templateProfile.saveProfile(this);
			registrationPrompt();
		}
		else {
			UserProfile tempProf = new UserProfile("userProfile");
			tempProf = tempProf.loadProfile(this);
			if(!tempProf.hasSignedUp()) { //if no user has signed up, then prompt with registration
				registrationPrompt();
			}
			else {
				setupMAdapter();
			}
		}
		
		
		
		// setup mAdapter
		//setUserInfo();
		
		//initialize location service
		locationIntent = new Intent(this, LocationService.class);
		startService(locationIntent);
		location = new LocationService(this);
	}
	
	private void setupMAdapter() {

		mAdapter = new MainPagerAdapter(getSupportFragmentManager());

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(mAdapter);
		viewPager.setCurrentItem(1);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	protected void onDestroy(){
		stopService(locationIntent);
		super.onDestroy();
	}
	
	private boolean userProfileExists() {
		File directory = getFilesDir();
		File filesList[] = directory.listFiles();
		int listSize = filesList.length;
		for(int i = 0; i < listSize; i++ ) {
			if(filesList[i].getName().equals("userProfile")) { //this will be the name of the file to hold the user info
				System.out.println("there is a file with name 'userProfile'");
				//System.out.println(filesList[i].);
				UserProfile tempProf = new UserProfile("userProfile");
				tempProf = tempProf.loadProfile(this);
				if(tempProf!=null) {
					System.out.println("as user has signed up = " + tempProf.hasSignedUp());
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void goToTrap(View view) {
		Intent i = new Intent(this, TrapCreaturesActivity.class);
		startActivity(i);
	}

	public void goToSettings(View view) {
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
	}

	public void goToMap(View view) {
		Intent i = new Intent(this, MapActivity.class);
		startActivity(i);
	}

	public void goToCreatureStats(View view) {
		Intent i = new Intent(this, CreatureStatsActivity.class);
		startActivity(i);
	}
	
	//creates an alert box to register for a profile
	public void registrationPrompt() {
		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.register);
		dialog.setTitle("Register");
		final EditText text1 = (EditText) dialog.findViewById(R.id.editText1);
		final EditText text2 = (EditText) dialog.findViewById(R.id.editText2);
		final EditText text3 = (EditText) dialog.findViewById(R.id.editText3);
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String firstName = text1.getText().toString();
				String lastName = text2.getText().toString();
				String userName = text3.getText().toString();
				if(firstName.length()==0 | lastName.length()==0 | userName.length()==0) {
					return;
				}
				else {
					UserProfile newProfile = new UserProfile("userProfile");
					newProfile.setInitialProfile(firstName, lastName, userName);
					newProfile.saveProfile(v.getContext());
					UserProfile testProfile = new UserProfile("userProfile");
					testProfile = testProfile.loadProfile(v.getContext());
					System.out.println("firstName = " + testProfile.getFirstName());
					setupMAdapter();
					dialog.dismiss();
				}
				
			}
		});
 
		dialog.show();
			
	}
}