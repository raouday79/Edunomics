package in.edunomics.chat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.edunomics.chat.modal.EdunomicsUserModal;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    private EditText et_content;
    private RecyclerView recycler_view;
    private ImageView btn_send;
    private TextWatcher contentWatcher = new C08204();
    TextView pname;
    ImageButton lyt_back;

    /* renamed from: com.material.components.activity.chat.ChatTelegram$4 */
    class C08204 implements TextWatcher {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C08204() {
        }

        public void afterTextChanged(Editable editable) {
            if (editable.toString().trim().length() == 0) {
                ChatActivity.this.btn_send.setImageResource(R.drawable.ic_mic);
            } else {
                ChatActivity.this.btn_send.setImageResource(R.drawable.ic_send);
            }
        }
    }

    class C08171 implements View.OnClickListener {
        C08171() {
        }

        public void onClick(View view) {
            ChatActivity.this.sendChat();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_telegram);
        Intent intent = getIntent();
        EdunomicsUserModal eu = (EdunomicsUserModal) intent.getSerializableExtra("person");

        init();

        pname.setText(eu.getName());


    }
    private void sendChat() {
        final String obj = this.et_content.getText().toString();
        if (!obj.isEmpty()) {
            Toast.makeText(this,obj,Toast.LENGTH_SHORT).show();
            this.et_content.setText("");
        }

    }

    public void onPostCreate(@Nullable Bundle bundle, @Nullable PersistableBundle persistableBundle) {
        super.onPostCreate(bundle, persistableBundle);
        hideKeyboard();
    }

    private void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    private void init() {
        lyt_back = findViewById(R.id.lyt_back);
        pname = findViewById(R.id.pname);
        this.recycler_view = (RecyclerView) findViewById(R.id.recyclerView);
        this.recycler_view.setLayoutManager(new LinearLayoutManager(this));
        this.recycler_view.setHasFixedSize(true);
        this.btn_send = (ImageView) findViewById(R.id.btn_send);
        this.et_content = (EditText) findViewById(R.id.text_content);
        this.btn_send.setOnClickListener(new C08171());
        this.et_content.addTextChangedListener(this.contentWatcher);
        lyt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
