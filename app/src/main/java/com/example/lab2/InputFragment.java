package com.example.lab2;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class InputFragment extends Fragment {

    private Spinner spinnerBooks;
    private RadioGroup radioGroupYears;
    private Button btnOk;
    private Button btnCancelMain;

    public InputFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        spinnerBooks = view.findViewById(R.id.spinnerBooks);
        radioGroupYears = view.findViewById(R.id.radioGroupYears);
        btnOk = view.findViewById(R.id.btnOk);
        btnCancelMain = view.findViewById(R.id.btnCancelMain);

        String[] books = {
                "----",
                "Дж.К. Роулінг - Гаррі Поттер",
                "Джордж Орвелл - 1984",
                "Всеволод Нестайко - Тореадори з Васюківки",
                "Герберт Шілдт - Java. Повне керівництво"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, books);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBooks.setAdapter(adapter);

        btnOk.setOnClickListener(v -> {
            String selectedBook = spinnerBooks.getSelectedItem().toString();
            int selectedRadioId = radioGroupYears.getCheckedRadioButtonId();

            StringBuilder errorMessage = new StringBuilder("Будь ласка, введіть наступні дані:\n");

            if (selectedBook.equals("----")) {
                errorMessage.append("- Оберіть книгу\n");
            }
            if (selectedRadioId == -1) {
                errorMessage.append("- Оберіть рік видання");
            }
            if (errorMessage.toString().equals("Будь ласка, введіть наступні дані:\n")) {
                RadioButton selectedRadioButton = view.findViewById(selectedRadioId);
                String selectedYear = selectedRadioButton.getText().toString();

                ResultFragment resultFragment = ResultFragment.newInstance(selectedBook, selectedYear);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, resultFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Незавершені дані")
                        .setMessage(errorMessage.toString())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        btnCancelMain.setOnClickListener(v -> {
            spinnerBooks.setSelection(0);
            radioGroupYears.clearCheck();
        });

        return view;
    }
}
