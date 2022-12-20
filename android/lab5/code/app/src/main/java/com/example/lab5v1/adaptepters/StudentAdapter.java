package com.example.lab5v1.adaptepters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5v1.R;
import com.example.lab5v1.entity.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {


    public interface OnItemClickListener{
        void onClick(Student student, int pos);
    }

    private final OnItemClickListener onClickListener;

    private final LayoutInflater inflater;
    private final ArrayList<Student> students;

    public StudentAdapter(OnItemClickListener onClickListener, LayoutInflater inflater, ArrayList<Student> states) {
        this.onClickListener = onClickListener;
        this.inflater = inflater;
        this.students = states;
    }


    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.simple_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Student student = students.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(student, position);
            }
        });
        holder.nameView.setText(student.getName());
        holder.capitalView.setText(student.getSurname());
        holder.imageView.setImageResource(R.drawable.roflanebalo);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, capitalView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            capitalView = view.findViewById(R.id.surname);
            imageView = view.findViewById(R.id.photo);
        }
    }


}