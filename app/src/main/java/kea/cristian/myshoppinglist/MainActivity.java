package kea.cristian.myshoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{
    private static String TEXT_KEY = "text";
    private static String EMPTY_EDIT_TEXT = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildEditTextContents();
    }

    private void buildEditTextContents()
    {
        SharedPreferences mPrefs = getPreferences(Context.MODE_PRIVATE);
        if (mPrefs.contains(MainActivity.TEXT_KEY))
        {
            String text = mPrefs.getString(MainActivity.TEXT_KEY, "");
            if (!text.equals(""))
            {
                restoreEditTextContent(text);
            }
        }
    }

    private void restoreEditTextContent(String text)
    {
        // text = "asdasdas\nasdsadas\n"
        Resources res = getResources();
        String[] texts = text.split(Pattern.quote("\n"));
        for (int i = 1; i < 11; i++)
        {
            String id = String.format("editText%s", i);
            int identifier = res.getIdentifier(id, "id", getApplicationContext().getPackageName());
            EditText editText = (EditText) findViewById(identifier);
            String editTextContent = texts[i - 1];
            if (editTextContent.equals(MainActivity.EMPTY_EDIT_TEXT))
            {
                editTextContent = "";
            }
            editText.setText(editTextContent);
        }

    }

    @Override
    protected void onDestroy()
    {
        saveEditTextsContents();
        super.onDestroy();
    }

    private void saveEditTextsContents()
    {
        String texts = getEditTextsContents();
        SharedPreferences mPrefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(MainActivity.TEXT_KEY, texts);
        editor.commit();
    }

    private String getEditTextsContents()
    {
        Resources res = getResources();
        StringBuilder sb = new StringBuilder();
        // return "<some text>\n<some more text>\n"
        for (int i = 1; i < 11; i++)
        {
            String id = String.format("editText%s", i);
            int identifier = res.getIdentifier(id, "id", getApplicationContext().getPackageName());
            EditText editText = (EditText) findViewById(identifier);
            String editTextContents = editText.getText().toString();
            if (editTextContents.equals(""))
            {
                editTextContents = MainActivity.EMPTY_EDIT_TEXT;
            }
            sb.append(editTextContents + "\n");
        }
        return sb.toString();
    }
}
