package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yingbwan on 8/13/2017.
 */

public class EditTodoDialogFragment extends DialogFragment {

    private EditText mName;
    private EditText mDueDate;
    private Spinner mPriority;
    private Button btnSave;
    private Todo mTodo;


    public EditTodoDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param Todo object.
     * @return A new instance of fragment EditTodoDiaglogFragment.
     */
    public static EditTodoDialogFragment newInstance(Todo td) {
        EditTodoDialogFragment fragment = new EditTodoDialogFragment();
        Bundle args = new Bundle();
        args.putString("id", "testcase");
        fragment.setArguments(args);
        fragment.setTodo(td);
        return fragment;
    }

    public void setTodo(Todo td) {
        mTodo = td;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_edit_item, container);
        btnSave = (Button) view.findViewById(R.id.btnSave);

        //---event handler for the button
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                mTodo.setName(mName.getText().toString());
                mTodo.setDueDate(new Date((mDueDate.getText().toString())));
                mTodo.setPriority(mPriority.getSelectedItem().toString());
                MainActivity act = (MainActivity) getActivity();
                act.onFinishDialog(mTodo);
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        mName = (EditText) view.findViewById(R.id.etName);
        mDueDate = (EditText) view.findViewById(R.id.tvDate);
        mPriority = (Spinner) view.findViewById(R.id.prioritydropdown);
        //load dropdown list
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.priority_array, android.R.layout.simple_spinner_item);
        // Set the layout to use for each dropdown item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPriority.setAdapter(adapter);

        if (mTodo == null) {
            mTodo = new Todo();
        } else {
            mName.setText(mTodo.getName());
            SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
            mDueDate.setText(ft.format(mTodo.getDueDate()));
            int spinnerPosition = adapter.getPosition(mTodo.getPriority());
            mPriority.setSelection(spinnerPosition);
        }

        // Show soft keyboard automatically and request focus to field
        mName.requestFocus();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    public interface EditTodoDialogListener {

        void onFinishDialog(Todo td);

    }

}
