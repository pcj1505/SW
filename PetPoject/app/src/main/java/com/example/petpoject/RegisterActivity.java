package com.example.petpoject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {




    private FirebaseAuth mFirebaseAuth;         // Firebase Certification
    private DatabaseReference mDatabaseRef;     // Real-time Database
    private EditText mEtEmail, mEtPwd, mEtPet;          // Register inputField
    private Button mBtnRegister;                // Register button
    private int mNumber;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("PetProject");


        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnRegister = findViewById(R.id.btn_register);
        mEtPet = findViewById(R.id.et_pet);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                // Start Register Process
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strPet = mEtPet.getText().toString();
                // Firebase Auth in Progress
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                           FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                           UserAccount account = new UserAccount();
                           mNumber = 0;
                           account.set_emailId(firebaseUser.getEmail());
                           account.set_idToken(firebaseUser.getUid());
                           account.set_password(strPwd);
                           account.set_pet(strPet);


                           // setValue = insert at database
                           mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                           Toast.makeText(RegisterActivity.this,"회원가입에 성공하셨습니다!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();  // destroy this activity
                        }
                        else
                        {
                            if(strEmail.length() == 0)
                            {
                                Toast.makeText(RegisterActivity.this,"이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                            }
                            if(strPwd.length() == 0)
                            {
                                Toast.makeText(RegisterActivity.this,"비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                            }
                            if(strPet.length() == 0)
                            {
                                Toast.makeText(RegisterActivity.this,"견종을 입력하세요!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }
}