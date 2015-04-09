package com.jedittext.sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.jay.jedittext.JEditText;

public class CommonUtils 
{
	public static boolean m_isError = false;
	
	/**
	 * Checks <code>p_editText</code> is empty or not. If empty then set error
	 * message for <code>p_editText</code>
	 * 
	 * @param p_editText
	 *            {@link EditText} to validate
	 * @param p_nullMsg
	 *            message to be display when field is empty
	 * @throws CustomException
	 *             when any exception occurs during validation
	 */
	public static boolean validateForEmptyValue(JEditText p_editText, String p_nullMsg) 
	{
		boolean m_error = false;
		try
		{
			if (p_editText != null && p_nullMsg != null)
			{
				
				// use trim() while checking for blank values
				if (TextUtils.isEmpty(p_editText.getText().toString().trim()))
				{
					m_isError = true;
					m_error = true;
					p_editText.setError(p_nullMsg);
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return !m_error;
	}
	
	/**
	 * Checks <code>p_editText</code> is empty or not and also checks
	 * <code>p_editText</code> contains valid email id or not. If null or
	 * invalid then set defined error messages for both cases.
	 * Ref : //http://regexlib.com/REDetails.aspx?regexp_id=192&AspxAutoDetectCookieSupport=1
	 * 
	 * @param p_context
	 *            activity context
	 * @param p_editText
	 *            {@link EditText} to validate
	 * @param p_nullMsg
	 *            message to be display when field is empty (for optional field
	 *            it should be <code>null</code>)
	 * @param p_invalidMsg
	 *            message to be display when email id is invalid
	 * @param p_invalidMsg
	 *            error message to be display when field is invalid
	 * @throws CustomException
	 *             when any exception occurs during validation.
	 */
	
	public static boolean validateEmail(Context p_context, JEditText p_editText, String p_nullMsg, String p_invalidMsg)
	{
		boolean m_error = false;
		try
		{
			if (p_editText != null)
			{
				//Pattern m_pattern = Pattern.compile("([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})");
				/**
				 * Matches 99.99% of e-mail addresses (excludes IP e-mails, which are rarely used). <br> 
				 * The {2,7} at the end leaves space for top level domains as short as .ca but leaves room for new ones like .museum, etc. <br>
				 *  The ?: notation is a perl non-capturing notation, and can be removed safely for non-perl-compatible languages. <br> 
				 *  See also email.
				 */
				Pattern m_pattern = Pattern.compile("^[\\w-]+(?:\\.[\\w-]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,7}$");
				
				Matcher m_matcher = m_pattern.matcher(p_editText.getText().toString().trim());
				if (!m_matcher.matches() && p_editText.getText().toString().trim().length() > 0)
				{
					m_isError = true;
					m_error = true;
					p_editText.setError(p_invalidMsg);
				}
				if (p_nullMsg != null)
				{
					validateForEmptyValue(p_editText, p_nullMsg);
				}
			}
		}
		
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return !m_error;
	}
}
