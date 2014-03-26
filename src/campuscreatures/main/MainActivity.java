package campuscreatures.main;

import java.util.List;

import campuscreatures.database.Creatures;
import campuscreatures.database.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DatabaseHelper database = new DatabaseHelper(this);

		/*
		 * test creatures database, and tests for all functions (add, delete, getAll)
		 * ID,Name, Region, District, Type, Health, Magic, Attack, Defense, Speed, Moves Per Turn, Experience, Level
		 * 
		 */
		database.addCreature(new Creatures("SabortoothTabor", "Ritter Hall", "Saint Louis University", "earth", 1, 2, 3, 4, 5, 6, 7, 8));
		database.addCreature(new Creatures("Desi Djinn ", "Simon Rec", "Saint Louis University", "earth",  21, 22, 23, 24, 25, 26, 27, 28));
		
		//get all Creatures
		//List<Creatures> list = database.getAllCreatures();
		database.close();
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
	
	public void goToMap(View view){
		Intent i = new Intent(this, MapActivity.class);
		startActivity(i);
	}
	
	public void goToCreatureStats(View view){
		Intent i = new Intent(this, CreatureStatsActivity.class);
		startActivity(i);
	}
	
	

}
