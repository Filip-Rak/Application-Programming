package kosmo.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity
{
    // ---------------------------
    // Attributes

    // Components
    private EditText noteTitle;
    private EditText noteContent;
    private Button saveNoteButton;
    private ImageButton deleteNoteButton;

    // Misc
    private int notePosition;

    // ---------------------------
    // Initialization
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Set the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        // Initialization
        initComponents();
        retrieveData();
        initListeners();
    }

    private void initComponents()
    {
        noteTitle = findViewById(R.id.editTitle);
        noteContent = findViewById(R.id.editContent);
        saveNoteButton = findViewById(R.id.saveButton);
        deleteNoteButton = findViewById(R.id.deleteButton);
    }

    private void retrieveData()
    {
        // Get data from the intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("noteTitle");
        String content = intent.getStringExtra("noteContent");

        // Adjust the attributes
        notePosition = intent.getIntExtra("notePosition", -1);
        noteTitle.setText(title);
        noteContent.setText(content);
    }

    private void initListeners()
    {
        // Handle the save button click
        saveNoteButton.setOnClickListener(this::handleSaveButton);

        // Handle the delete button click
        deleteNoteButton.setOnClickListener(this::handleDeleteButton);
    }

    private void handleSaveButton(View ignore)
    {
        // Retrieve strings from components
        String finalTitle = noteTitle.getText().toString();
        String finalContent = noteContent.getText().toString();

        // Create a result intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newNoteTitle", finalTitle);
        resultIntent.putExtra("newNoteContent", finalContent);
        resultIntent.putExtra("notePosition", notePosition);
        setResult(RESULT_OK, resultIntent);

        // End the activity
        finish();
    }

    private void handleDeleteButton(View ignore)
    {
        // Create a result intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra("notePosition", notePosition);
        setResult(RESULT_FIRST_USER, resultIntent); // RESULT_FIRST_USER is used for deletion

        // End the activity
        finish();
    }
}
