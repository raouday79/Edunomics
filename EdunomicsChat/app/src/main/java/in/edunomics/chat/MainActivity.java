package in.edunomics.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.edunomics.chat.adapter.UserAdapter;
import in.edunomics.chat.modal.EdunomicsUserModal;
import in.edunomics.chat.utility.MyRecyclerListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyRecyclerListener , SearchView.OnQueryTextListener{

    List<EdunomicsUserModal> l =new ArrayList<>();
    RecyclerView recyclerView ;
    ImageButton logout;
    UserAdapter userAdapter;
    SearchView searchView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        mAuth = FirebaseAuth.getInstance();
        init();
        initData();

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.et_search);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Enter Member's Name");
        logout = findViewById(R.id.logout);
logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();

    }
});
    }

    private void initData() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("user")
                .orderByKey();

query.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
        EdunomicsUserModal u =singleSnapshot.getValue(EdunomicsUserModal.class);
        if(!u.getMail().equals(mAuth.getCurrentUser().getEmail()))
        {
            l.add(u);
        }

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setHasFixedSize(true);
        userAdapter = new UserAdapter(l,MainActivity.this);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
       /* for(int i=0;i<10;i++)
        {
            EdunomicsUserModal e = new EdunomicsUserModal();
            e.setMail("raouday79@gmail.com");
            e.setPassword("11");
            e.setName("Uday"+i);
            l.add(e);
        }*/

    }

    @Override
    public void onClick(int position) {
        EdunomicsUserModal e = l.get(position);
        Intent i = new Intent(MainActivity.this,ChatActivity.class);
        i.putExtra("person",e);
        startActivity(i);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String input = newText.toLowerCase();
        List<EdunomicsUserModal> nList = new ArrayList<>();
        for(EdunomicsUserModal user : l)
        {
            if(user.getName().toLowerCase().contains(input))
            {
                nList.add(user);
            }
        }
        l=nList;
        userAdapter.updateList(nList);
        return true;
    }


}
