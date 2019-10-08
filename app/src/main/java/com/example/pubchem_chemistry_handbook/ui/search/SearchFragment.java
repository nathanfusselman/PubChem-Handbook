package com.example.pubchem_chemistry_handbook.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Student;
import com.example.pubchem_chemistry_handbook.ui.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    RecyclerView student_rview;
    List<Student> list_student;
    RVAdapter rvAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        loadStudent();
        student_rview = (RecyclerView) view.findViewById(R.id.recyclerview);

        rvAdapter = new RVAdapter(getActivity(), list_student);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        student_rview.setLayoutManager(layoutManager);
        student_rview.setAdapter(rvAdapter);

        return view;
    }

    void loadStudent() {
        list_student = new ArrayList<>();
        list_student.add(new Student("Anil", "Tadipatri"));
        list_student.add(new Student("Bhaskar", "Tadipatri"));
        list_student.add(new Student("Manohar", "Tadipatri"));
        list_student.add(new Student("Gopal", "Tadipatri"));
        list_student.add(new Student("Krishna", "Tadipatri"));
        list_student.add(new Student("JayaRam", "Tadipatri"));
        list_student.add(new Student("Kadiri", "Tadipatri"));
//        rvAdapter.notifyDataSetChanged();

    }
}