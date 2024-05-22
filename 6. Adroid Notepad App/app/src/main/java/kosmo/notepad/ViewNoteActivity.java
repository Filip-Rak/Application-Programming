package kosmo.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteContent;
    private Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        noteTitle = findViewById(R.id.editTitle);
        noteContent = findViewById(R.id.editContent);
        saveNoteButton = findViewById(R.id.saveButton);

        // Retrieve the title from the intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("noteTitle");
        noteTitle.setText(title);

        // Handle the save button click
        saveNoteButton.setOnClickListener(view -> {
            String finalTitle = noteTitle.getText().toString();
            String content = noteContent.getText().toString();
            Note newNote = new Note(finalTitle, content);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("newNoteTitle", newNote.getTitle());
            resultIntent.putExtra("newNoteContent", newNote.getContent());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
