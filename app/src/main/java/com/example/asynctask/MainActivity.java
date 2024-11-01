package com.example.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ProgressBar progressBar;
    private ImageView imageView;
    private Button button;
    private final int MAX_PROGRESS = 100;
    private final int DURATION = 10; // Durasi 10 detik untuk 100%

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.counting);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        // Set button click listener to start AsyncTask
        button.setOnClickListener(v -> new CounterTask().execute());
    }

    // AsyncTask untuk menghitung angka bertambah setiap detik
    private class CounterTask extends AsyncTask<Void, Integer, Void> {

        // Sebelum background task dimulai
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText("Task Sedang Dijalankan...");
            progressBar.setVisibility(View.VISIBLE);  // Tampilkan ProgressBar
            imageView.setVisibility(View.GONE);       // Sembunyikan ImageView
            button.setEnabled(false);                 // Nonaktifkan Button saat loading
        }

        // Melakukan tugas di thread background
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 1; i <= DURATION; i++) {
                    Thread.sleep(1000); // Jeda 1 detik
                    int progress = (i * MAX_PROGRESS) / DURATION;
                    publishProgress(progress); // Update UI dengan progress
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Mengupdate UI dengan data di background thread
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            progressBar.setProgress(progress);  // Set progres pada ProgressBar
            textView.setText("Loading... " + progress + "%"); // Update persentase
        }

        // Setelah background task selesai
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);    // Sembunyikan ProgressBar
            imageView.setVisibility(View.VISIBLE);   // Tampilkan Gambar
            textView.setText("Task Selesai Dijalankan");
            button.setEnabled(true);                 // Aktifkan Button kembali
        }
    }
}