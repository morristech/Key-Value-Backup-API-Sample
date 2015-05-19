package org.mightyfrog.android.minimal.backupapi;

import android.app.backup.BackupManager;
import android.app.backup.RestoreObserver;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Backup API sample code.
 *
 * @author Shigehiro Soejima
 */
public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        new BackupManager(this).dataChanged(); // creates and enqueues a backup

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create:
                createTestFiles();
                break;
            case R.id.restore:
                new BackupManager(this).requestRestore(new RestoreObserver() {
                    @Override
                    public void restoreFinished(int error) {
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //
    //
    //

    /**
     * Creates three test files in the external storage directory.
     */
    private void createTestFiles() {
        final File one = new File(Environment.getExternalStorageDirectory(), "one.txt");
        final File two = new File(Environment.getExternalStorageDirectory(), "two.txt");
        final File three = new File(Environment.getExternalStorageDirectory(), "three.txt");
        try {
            one.createNewFile();
            two.createNewFile();
            three.createNewFile();
            Toast.makeText(this, R.string.files_created, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            android.util.Log.e(MainActivity.class.getSimpleName(), "" + e);
        }
    }
}
