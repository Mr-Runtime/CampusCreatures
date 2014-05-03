package campuscreatures.main;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import campuscreatures.battleMechanics.BattleAction;
import campuscreatures.battleMechanics.BattleCreature;
import campuscreatures.database.Creatures;
import campuscreatures.database.DatabaseHelper;
import campuscreatures.location.LocationService;
import campuscreatures.profile.UserProfile;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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

	DatabaseHelper dbHelper = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		System.out.println("gere 2");
		if (!userProfileExists()) { //make sure a file called "userProfile has been created
			UserProfile templateProfile = new UserProfile();
			templateProfile.saveProfile(this);
			registrationPrompt();
		}
		else {
			UserProfile tempProf = new UserProfile();
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
		
		//TODO generate and fill CampusCreatures database;
		dbHelper = new DatabaseHelper(this);
		if (dbHelper.getAllCreatures().size() == 0){  
			//dbHelper = new DatabaseHelper(this);
			Log.d("From MainActivity", "....");
			//SQLiteDatabase dbHelper = DatabaseHelper.getWritableDatabase();
			dbHelper.getAllCreatures();
			dbHelper.addCreature(new Creatures("Marcus Taborius", "Ritter Hall", "Saint Louis University", "earth", 10, 10, 10, 10, 10, 10, 10, 10));
			dbHelper.addCreature(new Creatures("Desi Djinn ", "Simon Rec", "Saint Louis University", "fire",  10, 10, 10, 10, 10, 10, 10, 10));
			dbHelper.addCreature(new Creatures("Philanderphil", "Pius Library", "Saint Louis University", "space", 10, 10, 10, 10, 10, 10, 10, 10 ));
			dbHelper.addCreature(new Creatures("Weazel Man", "Tegler Field", "Saint Louis University", "normal", 10, 10, 10, 10, 10, 10, 10, 10 ));
			dbHelper.addCreature(new Creatures("Scan Bot", "Ritter Hall", "Saint Louis University", "electric", 10, 10, 10, 10, 10, 10, 10, 10 ));
			dbHelper.addCreature(new Creatures("Chamber Wolf", "Ritter Hall", "Saint Louis University", "normal", 10, 10, 10, 10, 10, 10, 10, 10 ));
			dbHelper.addCreature(new Creatures("Adam the Intern", "DuBourgh Hall", "Saint Louis University", "normal", 10, 10, 10, 10, 10, 10, 10, 10 ));
			dbHelper.addCreature(new Creatures("Lescher the Lecturer", "Ritter Hall", "Saint Louis University", "psychic", 10, 10, 10, 10, 10, 10, 10, 10 ));
			dbHelper.addCreature(new Creatures("Clueless Freshman", "Griesidieck Hall", "Saint Louis University", "spirit", 10, 10, 10, 10, 10, 10, 10, 10 ));
			dbHelper.addCreature(new Creatures("Roadrunner", "Reinert Hall", "Saint Louis University", "normal", 10, 10, 10, 10, 10, 10, 10, 10 ));
			dbHelper.addCreature(new Creatures("Billiken", "Saint Louis University", "Saint Louis University", "earth", 10, 10, 10, 10, 10, 10, 10, 10 ));
		}
		
		

		//get all Creatures
		dbHelper.getAllCreatures();
		dbHelper.getCreaturesCount();
		dbHelper.getCreature(1);
		//dbHelper.getAllCreaturesByRegion("Ritter Hall");
		//dbHelper.getLocalCreatures("Ritter Hall", "Saint Louis University");
		
		dbHelper.close();
		//dbHelper.close();
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
				UserProfile tempProf = new UserProfile();
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

	public void goToBattle(View view) {
		Intent i = new Intent(this, BattleActivity.class);
		startActivity(i);
	}

	public void goToCreatureStats(View view) {
		BattleCreature tempCreature;
		UserProfile tempProfile = new UserProfile(view.getContext());
		int creatureListIndex = 0;
		switch(view.getId())
		{
			case R.id.imageButton1:
				creatureListIndex = 0;
				break;
			case R.id.imageButton2:
				creatureListIndex = 1;
				break;
			case R.id.imageButton3:
				creatureListIndex = 2;
				break;
			case R.id.imageButton4:
				creatureListIndex = 3;
				break;
			case R.id.imageButton5:
				creatureListIndex = 4;
				break;
			case R.id.imageButton6:
				creatureListIndex = 5;
				break;
			default:
			throw new RuntimeException("Unknow button ID");
		}
		if (creatureListIndex < tempProfile.getParty().size()) {
			tempCreature = tempProfile.getParty().getPartyMember(creatureListIndex);
		}
		else {
			return;
		}
		Intent i = new Intent(this, CreatureEntryActivity.class);
		i.putExtra("creature", tempCreature);
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
					UserProfile newProfile = new UserProfile();
					newProfile.setInitialProfile(firstName, lastName, userName);
					
					//TODO below addCreature() is just for test purposes. remove when no longer necessary
					//start of test code alpha
					BattleAction kick = new BattleAction("kick",1,0,10);
					BattleAction heal = new BattleAction("heal",0,2,10);
					BattleAction burn = new BattleAction("burn",2,0,5);
					BattleAction push = new BattleAction("push",2,0,5);
					BattleAction intimidate = new BattleAction("intimidate",1,0,10);
					ArrayList<BattleAction> simpleActions1 = new ArrayList();
					simpleActions1.add(kick);
					simpleActions1.add(heal);
					simpleActions1.add(burn);
					simpleActions1.add(push);
					newProfile.addCreature(new BattleCreature(0,"Phil", "house", "room", "earth", 5, 2, 1,4,10,10,0,simpleActions1));
					//end of test code alpha
					
					newProfile.saveProfile(v.getContext());
					UserProfile testProfile = new UserProfile();
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