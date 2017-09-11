package com.codepath.simpletodo.ui.dialogs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.Todo;
import com.codepath.simpletodo.ui.activities.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.R.layout.simple_spinner_item;
import static android.media.CamcorderProfile.get;

/**
 * Created by yingbwan on 8/13/2017.
 */

public class EditTodoDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText mName;
    private EditText mDueDate;
    private Spinner mPriority;
    private Button btnSave, btnCancel;
    private Todo mTodo;
    private DatePickerDialog dueDatePickerDialog;
    private SimpleDateFormat dateFormatter;

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
     //   args.putString("id", "testcase");
      //  fragment.setArguments(args);
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

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        //---event handler for the button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {dismiss();} });
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(mDueDate.getText().length()==0) {
                    mDueDate.setHint("Required");
                    return;
                }
                mTodo.setName(mName.getText().toString());

                Date dt = null;
                try {
                    dt = dateFormatter.parse(mDueDate.getText().toString());
                } catch (ParseException e) {
                    //skip . we have this field filled using datepicker.
                }
                mTodo.setDueDate(dt);
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
        mDueDate.setInputType(InputType.TYPE_NULL);
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

        mDueDate.setOnClickListener(this);
        //solve two click issue for edittext field.
        mDueDate.setFocusable(false);

        Calendar newCalendar = Calendar.getInstance();
        int year = mTodo.getDueDate()==null?newCalendar.get(Calendar.YEAR):mTodo.getDueDate().getYear()+1900;
        int month = mTodo.getDueDate()==null?newCalendar.get(Calendar.MONTH):mTodo.getDueDate().getMonth();
        int dayofmonth = mTodo.getDueDate()==null?newCalendar.get(Calendar.DAY_OF_MONTH):mTodo.getDueDate().getDate();

        dueDatePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                mDueDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },year, month, dayofmonth);

        // Show soft keyboard automatically and request focus to field
        mName.requestFocus();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    @Override
    public void onClick(View view) {
        if(view == mDueDate) {
            dueDatePickerDialog.show();
        }
    }

}
