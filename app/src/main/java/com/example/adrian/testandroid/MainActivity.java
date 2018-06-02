package com.example.adrian.testandroid;

// Importurile necesare pentru butoane , textview, exceptii etc.

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {
    // Am declarat TextView-ul, EditText-ul, butoanele buttonRead si buttonWrite
    // numele fisierului fileName si fisierul filex , fiecare cu tipul sau
    TextView textViewFileO;
    EditText editTextFileI;
    Button buttonRead, buttonWrite;
    String fileName = "file.txt";
    File filex;

    // Am declarat calea, si anume folderul unde se afla fisierul file.txt
    private String filepath = "file";


    /* Metoda setContentView convertește resursele din fișierul activity_main aflat in folderul layout
       într-o ierarhie de obiecte vizuale în memorie
       Pentru a folosi butoanele , textview-ul etc am utilizat instructiunile de mai jos*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewFileO = (TextView) findViewById(R.id.textView);
        editTextFileI = (EditText) findViewById(R.id.editText);
        buttonRead = (Button) findViewById(R.id.buttonRead);
        buttonWrite = (Button) findViewById(R.id.buttonWrite);
        if(!isExternalStorageWritable()){
            buttonWrite.setEnabled(false);
            buttonRead.setEnabled(false);
        } else {
            filex = new File(getExternalFilesDir(filepath), fileName);
        }
    }

    /*Verifica daca memoria externa este disponibila pentru scriere si daca este disponibila
      un pop-up va afisa urmatorul mesaj " External Storage is Writable " */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(getApplicationContext(), " External Storage is Writable", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    /* Metoda de scriere in fisier
       Click pe buttonWrite
       Scrie un text dintr-o eticheta intr-un fisier extern */
    public void writeFile(View v) {
        try {
            //Creeaza un stream OUT de la fisierul extern
            FileOutputStream outputStream = new FileOutputStream(filex);
            OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream);
            outputWriter.write(editTextFileI.getText().toString());
            outputWriter.close();

            //Dupa ce am introdus un text , un pop-up cu mesajul "Text added to file" se va afisa
            Toast.makeText(getApplicationContext(),"Text added to file",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            editTextFileI.setText("");
        }
    }
    /* Metoda de citire in fisier
       Click pe buttonRead
       Citeste din fisier extern un text si il afiseaza intr-o eticheta */
    public void readFile(View v) {

        // Am initializat o variabila de tip string sirCitit
        String sirCitit = "";
        try {
            //creeaza un stream IN de la fisierul extern
            FileInputStream fis = new FileInputStream(filex);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            /* Am initializat o alta variabila de tip string strLine
               Iar acum in timp ce numarul liniei pe care se afla sirul de caractere introdus nu este null
               atunci sirCitit va trece la linia urmatoare adaugand sirul de caractere introdus pe aceasta linie,
               realizand operatia de concatenare a doua siruri */
            String strLine;
            while ((strLine = br.readLine()) != null) {
                sirCitit += "\n"+strLine ;
            }
            in.close();
            // Afiseaza sirul de caractere cu ajutorul textView-ului
            textViewFileO.setText("Content: \n"+sirCitit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

