package es.iessaladillo.pedrojoya.pr02_greetimproved;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr02_greetimproved.databinding.MainActivityBinding;
import es.iessaladillo.pedrojoya.pr02_greetimproved.utils.SoftInputUtils;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    private int counter = 0;
    private int counterName = 20;
    private int counterSurname = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpViews();
    }

    protected void onStart() {
        super.onStart();
        binding.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int resultName = counterName - binding.txtName.getText().length();
                binding.lblCounterName.setText(getResources().getQuantityString(R.plurals.lblCounterName, resultName, resultName));
                validateName();
            }
        });

        binding.txtSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int resultSurname = counterSurname - binding.txtSurname.getText().length();
                binding.lblCounterSurname.setText(getResources().getQuantityString(R.plurals.lblCounterSurname, resultSurname, resultSurname));
                validateSurname();
            }
        });
    }

    private void validateName() {
        if(binding.txtName.getText().toString().isEmpty()){
            binding.txtName.setError(getString(R.string.invalidName));
        } else {
            binding.txtName.setError(null);
        }
    }

    private void validateSurname() {
        if(binding.txtSurname.getText().toString().isEmpty()){
            binding.txtSurname.setError(getString(R.string.invalidSurname));
        } else {
            binding.txtSurname.setError(null);
        }
    }

    private void setUpViews() {
        binding.lblCounterName.setText(getResources().getQuantityString(R.plurals.lblCounterName, counterName,counterName));
        binding.lblCounterName.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.lblCounter.setText(getString(R.string.lblCounter, counter));
        binding.lblCounterSurname.setText(getResources().getQuantityString(R.plurals.lblCounterSurname, counterSurname, counterSurname));
        binding.swtPremium.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isPremium();
        });
        binding.txtName.setOnFocusChangeListener((v, hasFocus) -> {
            setTextColour(binding.lblCounterName, hasFocus);
        });
        binding.txtSurname.setOnFocusChangeListener((v, hasFocus) -> {
            setTextColour(binding.lblCounterSurname, hasFocus);
        });
        binding.txtSurname.setOnEditorActionListener((v, actionId, event) -> {
            message();
            return true;
        });

        binding.btnGreet.setOnClickListener(v -> update());
    }

    private void setTextColour(TextView textView, boolean hasFocus) {
        if (hasFocus){
            textView.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            textView.setTextColor(getResources().getColor(R.color.colorNormal));
        }

    }

    private void isPremium() {
        if (binding.swtPremium.isChecked()) {
            binding.progressBar1.setVisibility(View.GONE);
            binding.lblCounter.setVisibility(View.GONE);
        } else {
            counter = 0;
            binding.lblCounter.setText(getString(R.string.lblCounter, counter));
            binding.progressBar1.setVisibility(View.VISIBLE);
            binding.lblCounter.setVisibility(View.VISIBLE);
        }
    }

    private void update() {
        validateSurname();
        validateName();
        if (!(binding.txtName.getText().toString().equals("") || binding.txtSurname.getText().toString().equals(""))) {
            if (binding.swtPremium.isChecked()) {
                counter = 0;
                message();
            } else {
                message();
            }
        }

    }

    private void message() {
        SoftInputUtils.hideSoftKeyboard(binding.lblName);
        if (counter >= 10) {
            Toast.makeText(this, R.string.lblGreet10Times, Toast.LENGTH_SHORT).show();
        } else {
            counter++;
            binding.lblCounter.setText(getString(R.string.lblCounter, counter));
            if (binding.chkPolitely.isChecked()) {
                binding.lblGreetNoPolitely.setText(getString(R.string.lblGreetPolitely, binding.txtName.getText(), binding.txtSurname.getText()));
                Toast.makeText(this, binding.lblGreetNoPolitely.getText(), Toast.LENGTH_SHORT).show();
            } else {
                binding.lblGreetNoPolitely.setText(getString(R.string.lblGreetNoPolitely, binding.txtName.getText(), binding.txtSurname.getText()));
                Toast.makeText(this, binding.lblGreetNoPolitely.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}