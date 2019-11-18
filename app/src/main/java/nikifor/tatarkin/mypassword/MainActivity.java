package nikifor.tatarkin.mypassword;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private TextView mResultTextView;
    private EditText mSourseTextView;
    private View mCopyButton;
    private ImageView mQuality;
    private TextView mQualityText;
    private CompoundButton mUseUppercase;
    private CompoundButton mUseNumbers;
    private CompoundButton mUseSymbols;
    private Button mButtonNewPasswords;
    private TextView mResultTextNewPassword;
    private SeekBar mSizeNewPassword;
    private TextView mSizePassword;
    private View mCopyNewPasswordButton;

    private PasswordHelper mHelper;
    private GeneratePasswords mGeneratePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultTextView = findViewById(R.id.result_text);
        mSourseTextView = findViewById(R.id.sourse_text);
        mCopyButton = findViewById(R.id.button_copy);
        mQuality = findViewById(R.id.quality);
        mQualityText = findViewById(R.id.quality_text);
        mUseUppercase = findViewById(R.id.check_uppercase);
        mUseNumbers = findViewById(R.id.check_number);
        mUseSymbols = findViewById(R.id.check_symbol);
        mButtonNewPasswords = findViewById(R.id.new_password);
        mResultTextNewPassword = findViewById(R.id.result_text_new_password);
        mSizeNewPassword = findViewById(R.id.seek_new_password);
        mSizePassword = findViewById(R.id.size_password_text);
        mCopyNewPasswordButton = findViewById(R.id.button_copy_new_password);

        mHelper = new PasswordHelper(getResources().getStringArray(R.array.russian),getResources().getStringArray(R.array.english));
        mGeneratePassword = new GeneratePasswords();

        mCopyButton.setEnabled(false);
        mCopyNewPasswordButton.setEnabled(false);

        mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newPlainText(
                MainActivity.this.getString(R.string.clipboard_title), mResultTextView.getText()
                ));
                Toast.makeText(MainActivity.this, R.string.message_copied, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        mButtonNewPasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultPassword = mGeneratePassword.generate(mUseUppercase.isChecked(), mUseNumbers.isChecked(),
                        mUseSymbols.isChecked(), 8 + mSizeNewPassword.getProgress());
                mResultTextNewPassword.setText(resultPassword);
                mCopyNewPasswordButton.setEnabled(true);
            }
        });

        mSourseTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mResultTextView.setText(mHelper.convert(s));
                mCopyButton.setEnabled(s.length() > 0);
                int quality = mHelper.getQuality(s);
                mQuality.setImageLevel(quality * 1000);
                mQualityText.setText(getResources().getStringArray(R.array.qualities)[quality]);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSizeNewPassword.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String sizePassword = getResources().getQuantityString(R.plurals.character, progress, progress);
                mSizePassword.setText(sizePassword);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                String sizePassword = getResources().getQuantityString(R.plurals.character, 0, 0);
                mSizePassword.setText(sizePassword);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mCopyNewPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newPlainText(
                        MainActivity.this.getString(R.string.clipboard_title), mResultTextNewPassword.getText()
                ));
                Toast.makeText(MainActivity.this, R.string.message_copied, Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}
