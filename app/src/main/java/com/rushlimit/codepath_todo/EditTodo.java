package com.rushlimit.codepath_todo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import org.parceler.Parcels;

import database.Todo;


/**
 * A simple {@link DialogFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditTodo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditTodo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTodo extends DialogFragment {
    public static final String EDIT_TODO = "edit_todo";

    private Todo todo;

    private OnFragmentInteractionListener interactionListener;
    private EditText editTodoEditText;
    private Toolbar toolbar;

    public EditTodo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param todo The todoItem being edited.
     * @return A new instance of fragment EditTodo.
     */
    public static EditTodo newInstance(Todo todo) {
        EditTodo fragment = new EditTodo();
        Bundle args = new Bundle();
        args.putParcelable(EDIT_TODO, Parcels.wrap(todo));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            interactionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todo = Parcels.unwrap(getArguments().getParcelable(EDIT_TODO));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_todo, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbarInit();

        editTodoEditText = (EditText) rootView.findViewById(R.id.edit_todo_item_edit);
        editTodoEditText.setText(todo.getTodo());

        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                todoNotModifiedToast();
                super.onBackPressed();
            }
        };
    }

    @Override
    public void onResume() {
        // TODO: 12/22/16 Revisit to inline with styles
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    private void toolbarInit() {
        toolbar.setTitle("Edit Todo");
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        toolbar.setNavigationContentDescription(R.string.edit_todo_cancel);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todoNotModifiedToast();
                getDialog().dismiss();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onSaveEdit();
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu_edit_todo);
    }

    private void todoNotModifiedToast() {
        Toast.makeText(getActivity(), "Todo was not modified", Toast.LENGTH_SHORT).show();
    }

    /*
     * Validates view and text, and saves updated todoTask,
     * lets calling activity know to refresh list and dismisses self
     */
    public void onSaveEdit() {
        if (editTodoEditText == null) {
            Toast.makeText(getActivity(),
                    R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            return;
        }

        String textToAdd = editTodoEditText.getText().toString();
        if (textToAdd.isEmpty()) {
            Toast.makeText(getActivity(),
                    R.string.edit_todo_empty_save_error, Toast.LENGTH_SHORT).show();
            return;
        }

        todo.setTodo(textToAdd);
        todo.save();

        if (interactionListener != null) {
            interactionListener.updateListView();
        }

        getDialog().dismiss();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void updateListView();
    }
}
