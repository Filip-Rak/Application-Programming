package kosmo.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>
{
    // Attributes
    private final List<Note> notes;
    private final OnNoteClickListener onNoteClickListener;

    // Interfaces
    public interface OnNoteClickListener
    {
        void onNoteClick(Note note, int position);
    }

    // Classes
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewTitle;
        public TextView textViewContent;

        public ViewHolder(View view, final OnNoteClickListener onNoteClickListener, final List<Note> notes)
        {
            super(view);
            textViewTitle = view.findViewById(R.id.itemTitleTextView);
            textViewContent = view.findViewById(R.id.itemContentTextView);
            view.setOnClickListener(v ->
            {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION)
                    onNoteClickListener.onNoteClick(notes.get(position), position);
            });
        }
    }

    // Methods
    public NoteAdapter(List<Note> notes, OnNoteClickListener onNoteClickListener)
    {
        this.notes = notes;
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view, onNoteClickListener, notes);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewContent.setText(note.getContent().split("\n")[0]);
    }

    // Getters
    @Override
    public int getItemCount() {
        return notes.size();
    }
}
