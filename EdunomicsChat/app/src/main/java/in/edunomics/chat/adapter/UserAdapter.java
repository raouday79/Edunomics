package in.edunomics.chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.edunomics.chat.R;
import in.edunomics.chat.modal.EdunomicsUserModal;
import in.edunomics.chat.utility.MyRecyclerListener;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<EdunomicsUserModal> list;
   /* private final ClickListener listener;
    Context context;*/
    private MyRecyclerListener myRecyclerListener;

    public UserAdapter(List<EdunomicsUserModal> list, MyRecyclerListener myRecyclerListener) {
        this.myRecyclerListener = myRecyclerListener;
        this.list = list;

    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_people_chat, viewGroup, false);
       ///     itemView.setOnClickListener(mOnClickListener);
        return new UserAdapter.MyViewHolder(itemView,myRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder myViewHolder, int i) {

        EdunomicsUserModal modal = list.get(i);
        myViewHolder.name.setText(modal.getName());
        //myViewHolder.subtitle.setText(EdunomicsUserModal.getSubtopic());
        myViewHolder.description.setText(modal.getMail());

        //ImageLoader.getInstance().displayImage(EdunomicsUserModal.getImageURL(),myViewHolder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name,description;
        public ImageView image;
         MyRecyclerListener myRecyclerListener;

        public MyViewHolder(View view, MyRecyclerListener myRecyclerListener) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            //subtitle = view.findViewById(R.id.title3);
            image = view.findViewById(R.id.image);
            this.myRecyclerListener = myRecyclerListener;
            view.setOnClickListener(this);






        }

        @Override
        public void onClick(View v) {
            myRecyclerListener.onClick(getAdapterPosition());
        }


    }

    public void updateList(List<EdunomicsUserModal> newList)
    {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }

}
