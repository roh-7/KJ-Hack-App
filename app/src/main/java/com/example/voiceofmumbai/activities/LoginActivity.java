package com.example.voiceofmumbai.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apollographql.apollo.ApolloClient;
import com.example.voiceofmumbai.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity
{
	private ApolloClient apolloClient;

	private Button send_otp;
	private EditText phone;

	private FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		firebaseAuth = FirebaseAuth.getInstance();

		send_otp = findViewById(R.id.send_otp);
		phone = findViewById(R.id.login_number_edittext);

		send_otp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				Log.v("number","+91"+phone.getText().toString());
				verifyOtp("+91"+phone.getText().toString());
			}
		});

		// FIXME Login Later, Starting FeedActivity directly
//		startActivity(new Intent(this, FeedActivity.class));
	}

	private void verifyOtp(String phoneNumber)
	{
		PhoneAuthProvider.getInstance().verifyPhoneNumber(
				phoneNumber,
				120,
				TimeUnit.SECONDS,
				LoginActivity.this,
				new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
					@Override
					public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
						signInWithPhoneAuthCredential(phoneAuthCredential);
					}

					@Override
					public void onVerificationFailed(FirebaseException e) {
						// TODO: Say Please Try again Later
					}

					@Override
					public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
						// TODO: Start the Message Timer since we've got acknowledgement of sending OTP request successfully
//						originalVerificationId = s;
					}
				});
	}

	public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
		firebaseAuth.signInWithCredential(phoneAuthCredential)
				.addOnSuccessListener(LoginActivity.this, new OnSuccessListener<AuthResult>() {
					@Override
					public void onSuccess(AuthResult authResult) {
						Log.v("Log","onSuccess");
						startActivity(new Intent(LoginActivity.this,FeedActivity.class));
					}
				});
	}
}



/*
	OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.build();

		apolloClient = ApolloClient.builder()
				.okHttpClient(okHttpClient)
				.serverUrl(Constants.BASE_URL)
				.build();

		apolloClient.query(
				FeedTypeQuery.builder()
						.limit(5)
						.build()
		).enqueue(new ApolloCall.Callback<FeedTypeQuery.Data>()
		{
			@Override
			public void onResponse(@Nonnull Response<FeedTypeQuery.Data> response)
			{
				final StringBuffer buffer = new StringBuffer();
				assert response.data() != null;
				for (FeedTypeQuery.Profile profile : response.data().profiles())
				{
					buffer.append(" id: ");
					buffer.append(profile.id());
					buffer.append(" name: ");
					buffer.append(profile.name());
					buffer.append("\n\n");
					Log.v("response", buffer.toString());
				}
			}

			@Override
			public void onFailure(@Nonnull ApolloException e)
			{
				e.printStackTrace();
				Log.v("error", "onFailure");
			}
		});
*/
