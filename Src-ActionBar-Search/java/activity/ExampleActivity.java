package com.example.activity;

import android.app.SearchManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.OnSuggestionListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.R;
import com.example.adapter.SearchSuggestionAdapter;


public class ExampleActivity extends ActionBarActivity
{
	private final String[] SEARCH_SUGGESTION_CURSOR_COLUMNS = { BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_TEXT_2 };
	
	private SearchView mSearchView;
	private SearchSuggestionAdapter mSearchSuggestionAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// action bar menu
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_example, menu);
		
		// search view
		mSearchView = new SearchView(getSupportActionBar().getThemedContext());
		setupSearchView(mSearchView);
		
		// search menu item
		MenuItem searchMenuItem = menu.add(Menu.NONE, Menu.NONE, 1, R.string.ab_button_search);
		searchMenuItem.setIcon(R.drawable.ic_menu_search);
		MenuItemCompat.setActionView(searchMenuItem, mSearchView);
		MenuItemCompat.setShowAsAction(searchMenuItem, MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	private void setupSearchView(SearchView searchView)
	{
		// search hint
		searchView.setQueryHint(getString(R.string.ab_button_search_hint));
		
		// background
		View searchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
		searchPlate.setBackgroundResource(R.drawable.ab_searchview_bg);
		
		// clear button
		ImageView searchClose = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
		searchClose.setImageResource(R.drawable.ab_ic_clear_search);
		
		// text color
		AutoCompleteTextView searchText = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
		searchText.setTextColor(getResources().getColor(R.color.global_text_primary));
		searchText.setHintTextColor(getResources().getColor(R.color.global_text_secondary));
		
		// suggestion listeners
		searchView.setOnQueryTextListener(new OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String query)
			{
				// TODO
				Toast.makeText(ExampleActivity.this, "search: " + query, Toast.LENGTH_SHORT).show();
				
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String query)
			{
				if(query.length()>2)
				{
					updateSearchSuggestion(query);
				}
				return true;
			}
		});
		searchView.setOnSuggestionListener(new OnSuggestionListener()
		{
			@Override
			public boolean onSuggestionSelect(int position)
			{
				return false;
			}
			
			@Override
			public boolean onSuggestionClick(int position)
			{
				Cursor cursor = (Cursor) mSearchSuggestionAdapter.getItem(position);
				String title = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
				String subtitle = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2));
				
				// TODO
				Toast.makeText(ExampleActivity.this, "suggestion: " + title + " / " + subtitle, Toast.LENGTH_SHORT).show();
				
				return true;
			}
		});
	}
	
	
	private void updateSearchSuggestion(String query)
	{
		// cursor
		// TODO
		MatrixCursor cursor = new MatrixCursor(SEARCH_SUGGESTION_CURSOR_COLUMNS);
		cursor.addRow(new String[] { "1", query + "ica", "Lorem" });
		cursor.addRow(new String[] { "2", query + "dam", "Ipsum" });
		cursor.addRow(new String[] { "3", query + "rap", "Dolor" });
		
		// searchview content
		if(mSearchSuggestionAdapter==null)
		{
			// create adapter
			mSearchSuggestionAdapter = new SearchSuggestionAdapter(this, cursor);
			
			// set adapter
			mSearchView.setSuggestionsAdapter(mSearchSuggestionAdapter);
		}
		else
		{
			// refill adapter
			mSearchSuggestionAdapter.refill(this, cursor);
			
			// set adapter
			mSearchView.setSuggestionsAdapter(mSearchSuggestionAdapter);
		}
	}
}
