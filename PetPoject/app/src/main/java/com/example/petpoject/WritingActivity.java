package com.example.petpoject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;

public class WritingActivity extends AppCompatActivity{



    private FirebaseAuth mFirebaseAuth;         // Firebase Certification
    private DatabaseReference mDatabaseRef, mDatabaseRef2;
    private EditText mTitle, mContents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("PetProject");
        mTitle = findViewById(R.id.et_title);
        mContents = findViewById(R.id.et_contents);




        findViewById(R.id.btn_writingCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String strTitle = mTitle.getText().toString();
            String strContents = mContents.getText().toString();
            StringTokenizer st = new StringTokenizer(strContents);
            boolean badWords = false;
                while(st.hasMoreTokens())
                {
                    if (st.nextToken().equals("fuck"))
                    {

                        badWords = true;

                    }
                }
                if(badWords)
                {
                    Toast.makeText(WritingActivity.this,"내용에 욕설이 포함되어있습니다", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    PostInfo info = new PostInfo();
                    info.set_contents(strContents);
                    info.set_title(strTitle);


                    // setValue = insert at database

                    mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("_title").push().setValue(strTitle);
                    mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("_contents").push().setValue(strContents);

                    Toast.makeText(WritingActivity.this,"글쓰기에 성공하셨습니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WritingActivity.this, CommunityActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }




}