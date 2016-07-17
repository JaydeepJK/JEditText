package com.jay.jedittext.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.jay.jedittext.JEditText;
import com.jay.jedittext.sample.R;

public class MainActivity extends Activity 
{
	private JEditText m_etLogin,m_etPassword;
	private Button m_btnLogin;
	private Context m_context;

	private ToggleButton m_switchEnableTTS,m_switchEnableAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_context = this;
		m_etLogin = (JEditText) findViewById(R.id.am_etLogin);
		m_etPassword = (JEditText) findViewById(R.id.am_etPassword);
		m_btnLogin = (Button) findViewById(R.id.am_btnLogin);

		m_switchEnableTTS = (ToggleButton) findViewById(R.id.am_switchEnableTTS);
		m_switchEnableAnimation = (ToggleButton) findViewById(R.id.am_switchEnableAnimation);


//		CommonUtils.initTextToSpeech(m_context);

		if(m_switchEnableTTS.isChecked())
		{
			m_etLogin.setTTSEnabled(true);
			m_etPassword.setTTSEnabled(true);
		}
		else
		{
			m_etLogin.setTTSEnabled(false);
			m_etPassword.setTTSEnabled(false);
		}

		if(m_switchEnableAnimation.isChecked())
		{
			m_etLogin.setShakeAnimationEnabled(true);
			m_etPassword.setShakeAnimationEnabled(true);
		}
		else
		{
			m_etLogin.setShakeAnimationEnabled(false);
			m_etPassword.setShakeAnimationEnabled(false);
		}

		m_switchEnableTTS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(m_switchEnableTTS.isChecked())
				{
					m_etLogin.setTTSEnabled(true);
					m_etPassword.setTTSEnabled(true);
				}
				else
				{
					m_etLogin.setTTSEnabled(false);
					m_etPassword.setTTSEnabled(false);
				}
			}
		});

		m_switchEnableAnimation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(m_switchEnableAnimation.isChecked())
				{
					m_etLogin.setShakeAnimationEnabled(true);
					m_etPassword.setShakeAnimationEnabled(true);
				}
				else
				{
					m_etLogin.setShakeAnimationEnabled(false);
					m_etPassword.setShakeAnimationEnabled(false);
				}
			}
		});

		m_btnLogin.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				validatefields();
//				if(validatefields())
//				{
////					Toast.mak
//				}
			}
		});
	}
	
	/**
	 * Method to validate the given input fields
	 * @return True if the fields are valid
	 */
	public boolean validatefields()
	{
		try
		{
			if(CommonUtils.validateEmail(m_context, m_etLogin, "Dude!!! u forgot to write email", "Well!!! this is not a valid email")
					&& CommonUtils.validateForEmptyValue(m_etPassword, "Oh! u forgot to enter password"))
			{
				AlertDialog.Builder m_alert = new AlertDialog.Builder(m_context);
				m_alert.setTitle(getString(R.string.app_name));
				m_alert.setMessage("Thanks for entering correct details, That's it :)");
				m_alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				m_alert.setNegativeButton("No", null);
				m_alert.show();
				
			}
		}
		catch(Throwable p_e)
		{
			p_e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy()
	{
		if (CommonUtils.tts != null)
		{
			CommonUtils.tts.stop();
			CommonUtils.tts.shutdown();
		}
		if(m_etLogin.getTts() != null)
		{
			m_etLogin.getTts().stop();
			m_etLogin.getTts().shutdown();
		}
		if(m_etPassword.getTts() != null)
		{
			m_etPassword.getTts().stop();
			m_etPassword.getTts().shutdown();
		}
		super.onDestroy();
	}
}
