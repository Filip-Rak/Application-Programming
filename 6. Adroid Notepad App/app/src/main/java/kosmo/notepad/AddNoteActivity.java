package kosmo.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteTitle;
    private Button addNoteButton;
    private ActivityResultLauncher<Intent> viewNoteActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteTitle = findViewById(R.id.noteTitle);
        addNoteButton = findViewById(R.id.addNoteButton);

        viewNoteActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String title = result.getData().getStringExtra("newNoteTitle");
                        String content = result.getData().getStringExtra("newNoteContent");
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("newNoteTitle", title);
                        resultIntent.putExtra("newNoteContent", content);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                }
        );

        addNoteButton.setOnClickListener(view -> {
            String title = noteTitle.getText().toString();
            Intent intent = new Intent(AddNoteActivity.this, ViewNoteActivity.class);
            intent.putExtra("noteTitle", title);
            viewNoteActivityLauncher.launch(intent);
        });
    }
}
