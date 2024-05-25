package kosmo.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity
{
    // ---------------------------
    // Attributes
    private EditText noteTitle;
    private Button addNoteButton;

    // Activities
    private ActivityResultLauncher<Intent> viewNoteActivityLauncher;

    // ---------------------------
    // Initialization
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Prepare the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Initialization
        initComponents();
        initListeners();
        initActivities();
    }

    private void initComponents()
    {
        noteTitle = findViewById(R.id.noteTitle);
        addNoteButton = findViewById(R.id.addNoteButton);
    }

    private void initListeners()
    {
        // Add note button listener
        addNoteButton.setOnClickListener(view ->
        {
            // Get the title
            String title = noteTitle.getText().toString();

            // Pass to next activity
            Intent intent = new Intent(AddNoteActivity.this, ViewNoteActivity.class);
            intent.putExtra("noteTitle", title);
            viewNoteActivityLauncher.launch(intent);
        });
    }

    private void initActivities()
    {
        viewNoteActivityLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        this::handleAddNote
                );
    }

    // ---------------------------
    // Activities
    private void handleAddNote(ActivityResult result)
    {
        if (result.getResultCode() == RESULT_OK && result.getData() != null)
        {
            // Get results
            String title = result.getData().getStringExtra("newNoteTitle");
            String content = result.getData().getStringExtra("newNoteContent");

            // Pass results further
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newNoteTitle", title);
            resultIntent.putExtra("newNoteContent", content);
            setResult(RESULT_OK, resultIntent);

            // Stop the activity
            finish();
        }
    }
}
