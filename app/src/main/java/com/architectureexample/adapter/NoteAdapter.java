package com.architectureexample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.architectureexample.R;
import com.architectureexample.databinding.NoteItemBinding;
import com.architectureexample.roomDatabase.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

//public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
public class NoteAdapter extends ListAdapter<Note,NoteAdapter.MyViewHolder> {


    OnItemClickListener listener;

    public NoteAdapter() {
        super(DEFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DEFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority()==newItem.getPriority();
        }
    };


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NoteItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.note_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.textViewTitle.setText(getItem(position).getTitle());
        holder.binding.textViewDescription.setText(getItem(position).getDescription());
        holder.binding.textViewPriority.setText("" + getItem(position).getPriority());


    }






    public Note getPositionAt(int adapterPosition) {
        return getItem(adapterPosition);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        NoteItemBinding binding;

        public MyViewHolder(@NonNull NoteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


