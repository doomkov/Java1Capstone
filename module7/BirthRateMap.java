package module7;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import module6.CommonMarker;
import module6.EarthquakeMarker;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import java.util.HashMap;

import de.fhpotsdam.unfolding.marker.Marker;

public class BirthRateMap extends PApplet {

	UnfoldingMap map;
	HashMap<String, Float> birthRateHash;
	List<Feature> countries;
	List<Marker> countryMarkers;
	public static String mbTilesString = "blankLight-1-3.mbtiles";

	private boolean lastClicked = false;
	
	public void setup() {
		size(800, 600, OPENGL);
		//map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		map = new UnfoldingMap(this, 50, 50, 750, 550, new MBTilesMapProvider(mbTilesString));
		MapUtils.createDefaultEventDispatcher(this, map);

		// Load lifeExpectancy data
		birthRateHash = parseBirthRateCSV(this,"birthrate_data.csv");
		System.out.println(birthRateHash);

		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		System.out.println(countryMarkers.get(0).getId());
		
		// Country markers are shaded according to life expectancy (only once)
		shadeCountries();
	}
	
	public void draw() {
		// Draw map tiles and country markers
		map.draw();
		this.addKey();
	}
	
	private void shadeCountries() {
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();
			System.out.println(birthRateHash.containsKey(countryId));
			if (birthRateHash.containsKey(countryId)) {
				float birthRate = birthRateHash.get(countryId);
				// Encode value as brightness (values range: 40-90)
				if(birthRate < 10) {
					marker.setColor(color(50,50,255));
				}
				else if (birthRate >= 10 && birthRate < 20) {
					marker.setColor(color(240,240,0));
				}
				else if (birthRate >= 20 && birthRate < 25) {
					marker.setColor(color(255,155,0));
				}
				else if (birthRate >= 25) {
					marker.setColor(color(255,0,0));
				}
				
			}
			else {
				marker.setColor(color(150,150,150));
			}
		}
	}
	
	
	private static HashMap<String, Float> parseBirthRateCSV(PApplet p, String fileName) {
		// HashMap key: country ID and  data: lifeExp at birth
		HashMap<String, Float> birthRateHash = new HashMap<String, Float>();

		String[] rows = p.loadStrings(fileName);
		System.out.println(rows);

		for (String row : rows) {
			// split row by commas not in quotations
			String[] columns = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

			for(int i = columns.length - 1; i > 4; i--) {
				
				// check if value exists for year
				if(!columns[i].equals("..")) {
					birthRateHash.put(columns[1], Float.parseFloat(columns[i]));
					
					// break once most recent data is found
					break;
				}
			}
			
		}
		

		return birthRateHash;
	}
	
	private void addKey() {	
		// Remember you can use Processing's graphics methods here
		fill(255, 250, 240);
		rect(25, 50, 140, 240);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Birth Rate by Country", 30, 75);
		text("(per 1,000 people)", 30, 95);
		
		fill(color(50,50,255));
		rect(50, 120, 15, 15);
		fill(color(240,240,0));
		rect(50, 145, 15, 15);
		fill(color(255,155,0));
		rect(50, 170, 15, 15);
		fill(color(255,0,0));
		rect(50, 195, 15, 15);
		
		fill(0, 0, 0);
		text("Under 10", 75, 125);
		text("10 to 20", 75, 150);
		text("20 to 25", 75, 175);
		text("Over 25", 75, 200);
		
		text("Click on each colored ", 30, 225);
		text("box to restrict", 30, 245);
		text("visible countries", 30, 265);
	}
	
	@Override
	public void mouseClicked()
	{
		if (lastClicked == true) {
			this.unhideMarkers();
			lastClicked = false;
		}
		else {
			checkCountriesForRange();
		}
	}
	
	// Helper method that will check if a city marker was clicked on
	// and respond appropriately
	private void checkCountriesForRange(){
		float minRate = 0;
		float maxRate = 99;
		
		if (mouseX >= 50 && mouseX <= 65 && mouseY >= 120 & mouseY <= 135) {
			minRate = 0;
			maxRate = 10;
			lastClicked = true;
		}
		else if(mouseX >= 50 && mouseX <= 65 && mouseY >= 145 & mouseY <= 160) {
			minRate = 10;
			maxRate = 20;
			lastClicked = true;
		}
		else if(mouseX >= 50 && mouseX <= 65 && mouseY >= 170 & mouseY <= 185) {
			minRate = 20;
			maxRate = 25;
			lastClicked = true;
		}
		else if(mouseX >= 50 && mouseX <= 65 && mouseY >= 195 & mouseY <= 210) {
			minRate = 25;
			maxRate = 99;
			lastClicked = true;
		}
		
		if (lastClicked == true) {
			for (Marker m : countryMarkers) {
				
				float birthRate = -1;
				if (birthRateHash.containsKey(m.getId())) {
					birthRate = birthRateHash.get(m.getId());
				}
				
				System.out.println(minRate);
				System.out.println(birthRate);
				System.out.println(maxRate);
				
				if (birthRate < minRate || birthRate > maxRate) {
					
					m.setHidden(true);
					
				}
			}
		}
	}
	
	private void unhideMarkers() {
		for(Marker marker : countryMarkers) {
			marker.setHidden(false);
		}
			
	}
	
}

