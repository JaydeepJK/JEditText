package com.jay.jedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class JEditText extends FrameLayout implements OnTouchListener
{
	private EditText m_etText;
	private TextView m_tvErrorText;
	
	private long m_vibrationPattern[] = { 0, 100 };
	public Vibrator m_vibrator;
	private Context m_context;
	
	// public CustomEditText(Context p_context, AttributeSet p_set)
	// {
	// super(p_context, p_set);
	// }
	
	public JEditText(Context p_context, AttributeSet p_set)
	{
		super(p_context, p_set);
		LayoutInflater m_inflater = LayoutInflater.from(p_context);
		FrameLayout m_rlMainContainer = (FrameLayout) m_inflater.inflate(R.layout.error_edittext_layout, this, true);
		m_context = p_context;
		m_etText = (EditText) m_rlMainContainer.findViewById(R.id.eel_etEdittext);
		m_tvErrorText = (TextView) m_rlMainContainer.findViewById(R.id.eel_tvErrorMessage);
		
		// m_etText.setRawInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		
		m_tvErrorText.setOnTouchListener(this);
		
		// // This parses the attributeSet and filters out to the stuff we care about
		// TypedArray typedArray = p_context.obtainStyledAttributes(R.styleable.custom_edittext);
		// // Now we read let's say an int into our custom member variable.
		// int attr = typedArray.getInt(R.styleable.myCustomComponents_myCustomAttribute, 0);
		// // Make a public mutator so that others can set this attribute programatically
		// setCustomAttribute(attr);
		// // Remember to recycle the TypedArray (saved memory)
		// typedArray.recycle();
		
		// TypedArray m_attr = p_context.obtainStyledAttributes(p_set, R.styleable.custom_edittext, p_defStyle, 0);
		
		TypedArray m_attr = p_context.obtainStyledAttributes(p_set, R.styleable.custom_edittext);
		// String str = a.getString(R.styleable.custom_edittext_inputtype);
		// int attr = m_attr.getInt(R.styleable.custom_edittext_inputtype, 0);
		
		if (p_set != null)
		{
			TypedArray a = getContext().obtainStyledAttributes(p_set, R.styleable.custom_edittext, 0, 0);
			// setHint(a.getString(index));
			setDefaultInputType(a.getInt(R.styleable.custom_edittext_inputtype, 0x7f010094));
			a.recycle();
		}
		
		final int m_count = m_attr.getIndexCount();
		for (int i = 0; i < m_count; ++i)
		{
			int attr = m_attr.getIndex(i);
			if(attr == R.styleable.custom_edittext_inputtype)
			{
				if (p_context.isRestricted())
				{
					throw new IllegalStateException("The " + getClass().getCanonicalName() + ":required attribute cannot " + "be used within a restricted context");
				}
				
				int m_defaultValue = 0;
				final int m_required = m_attr.getInteger(0, m_defaultValue);
				// DO SOMETHING
				setDefaultInputType(m_required);
			}
			else if(attr == R.styleable.custom_edittext_hint)
			{
				String m_hint = "";
				m_hint = m_attr.getString(1);
				setHint(m_hint);
			}
			
		}
		m_attr.recycle();
		
		m_etText.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence p_s, int p_start, int p_before, int p_count)
			{
				if (p_s.length() > 0)
				{
					m_etText.setVisibility(View.VISIBLE);
					m_tvErrorText.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence p_s, int p_start, int p_count, int p_after)
			{
				
			}
			
			@Override
			public void afterTextChanged(Editable p_s)
			{
				
			}
		});
	}
	
	/**
	 * Method to set the default inputtype of the edittext
	 * 
	 * @param p_type
	 *            The inputtype of the edittext
	 */
	public void setDefaultInputType(final int p_type)
	{
		if (p_type == Constants.TEXT)
		{
			m_etText.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
		}
		if (p_type == Constants.EMAIL_ADDRESS)
		{
			m_etText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		}
		if (p_type == Constants.PASSWORD)
		{
			m_etText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
			m_etText.setTransformationMethod(PasswordTransformationMethod.getInstance());
		}
		if (p_type == Constants.NUMBER)
		{
			m_etText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		}
		if (p_type == Constants.PHONE)
		{
			m_etText.setInputType(InputType.TYPE_TEXT_VARIATION_PHONETIC);
		}
	}
	
	/**
	 * Method to set hint in the Edittext
	 * 
	 * @param p_hintMessage
	 *            The hint message
	 */
	public void setHint(String p_hintMessage)
	{
		m_etText.setHint(p_hintMessage);
	}
	
	@Override
	public boolean onTouch(View p_v, MotionEvent p_event)
	{
		if(p_v.getId() == R.id.eel_tvErrorMessage)
		{
			hideErrorMessage();
		}
		
		return true;
	}
	
	/**
	 * Method to hide the error message
	 */
	public void hideErrorMessage()
	{
		m_tvErrorText.setVisibility(View.GONE);
		m_etText.setVisibility(View.VISIBLE);
		//m_etText.setText("");
		m_etText.requestFocus();
		showKeyboard(m_context, m_etText);
	}
	
	/**
	 * Method to set error message in the Edittext
	 * 
	 * @param p_error
	 *            The error message to be displayed in the Edittext
	 */
	public void setError(String p_error)
	{
		// m_etText.setText("");
		startVibrate();
		m_tvErrorText.setVisibility(View.VISIBLE);
		m_tvErrorText.setText(p_error);
		m_etText.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * Get text from the gender selector
	 * 
	 * @return text from the gender selector
	 */
	public String getText()
	{
		return m_etText.getText().toString();
	}
	
	/**
	 * Method to vibrate the phone for a small duration
	 */
	public void startVibrate()
	{
		m_vibrator = (Vibrator) m_context.getSystemService(Context.VIBRATOR_SERVICE);
		m_vibrator.vibrate(m_vibrationPattern, -1);
	}
	
	/**
	 * Show virtual keyboard.
	 * 
	 * @param p_context
	 *            activity context
	 * @param p_view
	 *            view of the activity
	 */
	public static void showKeyboard(Context p_context, View p_view)
	{
		InputMethodManager imm = (InputMethodManager) p_context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(p_view, InputMethodManager.SHOW_FORCED);
	}

}
