# JEditText
An extension of Android's default EditText control with error indication
JeditText is an extension of the default Android Edittext with a different,stylish way to indicate the error message.

Feel free to use it all you want in your Android apps provided that you cite this project and include the license in your app.

# Setup

1.	Download the project from here and then include the project as a module in your application.
(Will update for the gradle soon :) )
2.	Include the project JeditText in the settings.gradle
3.	Now, in your main module's gradle file, add the dependency for the project using below line in dependencies block:
compile project(':JeditText')
4.	Now sync the project.

# Simple example

You can add the JeditText in the application module in both, layout xml and Java files as shown below:

In layout.xml file,

```html
<com.jay.jedittext.JEditText 
        android:id="@+id/am_etLogin" 
        android:layout_width="match_parent" 
        android:layout_height="wrap_content" 
        android:layout_below="@id/am_relOptions" 
        android:layout_marginTop="50dp" 
        android:layout_marginLeft="@dimen/activity_horizontal_margin" 
        android:layout_marginRight="@dimen/activity_horizontal_margin" 
	    custom_edittext:enabletts = "true"/>
```

Here, as you can see,we have added just 1 custom attribute of enabling the TTS(Text To Speech) other attributes are of the Normal Edittext.

You can enable/disable the vibration effect and TTS integration using the xml tags <b>enabletts</b> and <b>enableshakeanimation</b>.


Both the tags accept boolean values.

You can also use this in the java file as shown below:
```html
JEditText m_etLogin = (JEditText) findViewById(R.id.am_etLogin);
m_etLogin.setTTSEnabled(true);
m_etLogin.setShakeAnimationEnabled(true);
```
You can see the Jedittext example in a sample application in the video given here.

[![JEdittext in a sample application](http://img.youtube.com/vi/c8J__kgTn4E/0.jpg)](http://www.youtube.com/watch?v=c8J__kgTn4E)

Please let me know what extra capabilities you want to add for this control and I will add it.

Thanks and Enjoy :)

