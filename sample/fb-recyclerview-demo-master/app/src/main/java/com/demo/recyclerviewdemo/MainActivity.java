package com.demo.recyclerviewdemo;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nesquena.recyclerviewdemo.R;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    ContactsAdapter adapter;

    TextView resultView = null;
    Loader cursorLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        resultView = (TextView) findViewById(R.id.res);
    }

    void setupViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Setup RecyclerView, associated adapter, and layout manager.
        adapter = new ContactsAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                Toast.makeText(getApplicationContext(), "444444444", Toast.LENGTH_SHORT).show();
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getApplicationContext(), "5555555555", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//
//            @Override
//            public void onTouchEvent(RecyclerView recycler, MotionEvent event) {
//                // Handle on touch events here
//                android.util.Log.w("ABC", "11111");
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//                android.util.Log.w("ABC", "22222");
//            }
//
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView recycler, MotionEvent event) {
//                android.util.Log.w("ABC", "33333");
//                return false;
//            }
//
//        });

        // Populate contact list.
        adapter.addMoreContacts(Contact.createContactsList(20));

        // Setup button to append additional contacts.
        Button addMoreButton = (Button) findViewById(R.id.add_more_contacts);
        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addMoreContacts(Contact.createContactsList(5));
            }
        });
    }

    public void onClickDisplayNames(View view) {
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        cursorLoader = new CursorLoader(this, Uri.parse("content://com.example.contentproviderexample.MyProvider/cte"), null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        StringBuilder res = new StringBuilder();
        if(cursor != null)
        {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                res.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")));
                cursor.moveToNext();
            }
        }
        else
            res.append("Empty");

        resultView.setText(res);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
