package com.example.owen.sleep_lab;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDate;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{

	private static final int PICK_IMAGE_REQUEST = 234;
	private FirebaseAuth mAuth;
	private DatabaseReference database;
	private StorageReference storageReference;
	private TextView profileTextView;
	String TAG;
	private EditText inputText;
	private Button logoutBtn, sendToDb, chooseBtn, upLoadBtn, recordBtn, statBtn, scanBtn;
	private Uri filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		TAG = "profile activity";
		TAG = "profile activity";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		//editText
		profileTextView = (TextView) findViewById(R.id.profileTextView);
		inputText = (EditText) findViewById(R.id.inputText);

		//buttons
		logoutBtn = (Button) findViewById(R.id.logoutBtn);
		logoutBtn.setOnClickListener(this);
		sendToDb = (Button) findViewById(R.id.sentToDb);
		sendToDb.setOnClickListener(this);
		chooseBtn = (Button) findViewById(R.id.chooseBtn);
		chooseBtn.setOnClickListener(this);
		upLoadBtn = (Button) findViewById(R.id.upLoadBtn);
		upLoadBtn.setOnClickListener(this);
		recordBtn = (Button) findViewById(R.id.recordBtn);
		recordBtn.setOnClickListener(this);
		statBtn = (Button) findViewById(R.id.statBtn);
		statBtn.setOnClickListener(this);

		//Firebase
		mAuth = FirebaseAuth.getInstance();
		database = FirebaseDatabase.getInstance().getReference();
		storageReference = FirebaseStorage.getInstance().getReference();

		//************check if user is logged in
		if(mAuth.getCurrentUser() == null)
		{
			startActivity(new Intent(this, LoginActivity.class));
		}
		FirebaseUser user = mAuth.getCurrentUser();
		profileTextView.setText("Welcome " + user.getEmail());

	}

	@Override
	public void onClick(View view)
	{
		if(view == logoutBtn)
		{
			mAuth.signOut();
			finish();
			startActivity(new Intent(this, LoginActivity.class));
		}
		if(view == sendToDb)
		{
			saveData();
		}
		if(view == chooseBtn)
		{
			showFileChooser();
		}
		if(view == upLoadBtn)
		{
			uploadFile();
		}
		if(view == recordBtn)
		{
			Log.d(TAG, "move to record activity ");
			startActivity(new Intent(this, RecordActivity.class));
		}
		if(view == statBtn)
		{
			startActivity(new Intent(this, StatsActivity.class));
		}
	}

	private void uploadFile()
	{
		if(filePath != null)
		{
			StorageReference riversRef = storageReference.child("images/profileImage.jpg");

			riversRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
			{
				@Override
				public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
				{
					Toast.makeText(ProfileActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();

				}
			}).addOnFailureListener(new OnFailureListener()
			{
				@Override
				public void onFailure(@NonNull Exception exception)
				{
					Toast.makeText(ProfileActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();

				}
			});
		}
		else
		{
			Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
		}

	}

	private void showFileChooser()
	{
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "select file to upload"), PICK_IMAGE_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null)
		{
			filePath = data.getData();
		}
	}

	public void saveData()
	{
		String message = inputText.getText().toString();

		Save save = new Save(message);

		FirebaseUser user = mAuth.getCurrentUser();

		database.child(user.getUid()).setValue(save);
		Toast.makeText(this, "Message saved ", Toast.LENGTH_SHORT).show();

		Toast.makeText(this, "message saved", Toast.LENGTH_SHORT).show();
	}
}
