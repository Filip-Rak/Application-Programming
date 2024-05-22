package kosmo.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_VIEW_NOTE = 2;

    private TextView textView;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private LinkedList<Note> notes;
    private FloatingActionButton buttonOpenAddNote;

    private final ActivityResultLauncher<Intent> addNoteActivityLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            String title = result.getData().getStringExtra("newNoteTitle");
                            String content = result.getData().getStringExtra("newNoteContent");
                            Note newNote = new Note(title, content);
                            notes.addFirst(newNote);
                            adapter.notifyItemInserted(0);
                        }
                    }
            );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);
        buttonOpenAddNote = findViewById(R.id.buttonOpenAddNote);

        // Initialize dataset
        notes = new LinkedList<>();

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(notes);
        recyclerView.setAdapter(adapter);

        // Handle the button click to open AddNoteActivity
        buttonOpenAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            addNoteActivityLauncher.launch(intent);
        });
    }
}
