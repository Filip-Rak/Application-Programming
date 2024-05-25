package kosmo.notepad;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteClickListener
{
    // ---------------------------
    // Attributes

    // Storage
    private NoteAdapter adapter;
    private LinkedList<Note> notes;

    // Components
    private RecyclerView recyclerView;
    private FloatingActionButton buttonOpenAddNote;

    // Activities
    private ActivityResultLauncher<Intent> addNoteActivityLauncher;
    private ActivityResultLauncher<Intent> editNoteActivityLauncher;

    // ---------------------------
    // Initialization
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Prepare the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization
        initComponents();
        loadNotes();
        initRecyclerView();
        addListeners();
        initActivities();
    }

    private void initComponents()
    {
        recyclerView = findViewById(R.id.recyclerView);
        buttonOpenAddNote = findViewById(R.id.buttonOpenAddNote);
    }

    private void loadNotes()
    {
        notes = SharedPreferencesUtils.loadNotes(this);
    }

    private void initRecyclerView()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(notes, this);
        recyclerView.setAdapter(adapter);
    }

    private void addListeners()
    {
        // Add note button listener
        buttonOpenAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            addNoteActivityLauncher.launch(intent);
        });
    }

    private void initActivities()
    {
        // Adding a note
        addNoteActivityLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        this::handleAddNoteResult
                );

        // Editing a note
        editNoteActivityLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        this::handleEditNoteResult
                );
    }

    // ---------------------------
    // Events
    @Override
    public void onNoteClick(Note note, int position)
    {
        Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
        intent.putExtra("noteTitle", note.getTitle());
        intent.putExtra("noteContent", note.getContent());
        intent.putExtra("notePosition", position);
        editNoteActivityLauncher.launch(intent);
    }

    // ---------------------------
    // Note manipulation
    private void addNote(String title, String content)
    {
        Note newNote = new Note(title, content);
        notes.addFirst(newNote);
        adapter.notifyItemInserted(0);
        SharedPreferencesUtils.saveNotes(MainActivity.this, notes);
    }

    private void editNote(int position, String title, String content)
    {
        Note updatedNote = notes.get(position);
        updatedNote.setTitle(title);
        updatedNote.setContent(content);
        adapter.notifyItemChanged(position);
        SharedPreferencesUtils.saveNotes(MainActivity.this, notes);
    }

    private void deleteNote(int position)
    {
        notes.remove(position);
        adapter.notifyItemRemoved(position);
        SharedPreferencesUtils.saveNotes(MainActivity.this, notes);
    }

    // ---------------------------
    // Activities
    private void handleAddNoteResult(ActivityResult result)
    {
        if (result.getResultCode() == RESULT_OK && result.getData() != null)
        {
            String title = result.getData().getStringExtra("newNoteTitle");
            String content = result.getData().getStringExtra("newNoteContent");
            addNote(title, content);
        }
    }

    private void handleEditNoteResult(ActivityResult result)
    {
        // Edit the note
        if (result.getResultCode() == RESULT_OK && result.getData() != null)
        {
            int position = result.getData().getIntExtra("notePosition", -1);
            String title = result.getData().getStringExtra("newNoteTitle");
            String content = result.getData().getStringExtra("newNoteContent");

            if (position != -1) editNote(position, title, content);
        }

        // Delete the note
        else if (result.getResultCode() == RESULT_FIRST_USER && result.getData() != null)
        {
            int position = result.getData().getIntExtra("notePosition", -1);
            if (position != -1) deleteNote(position);
        }
    }
}