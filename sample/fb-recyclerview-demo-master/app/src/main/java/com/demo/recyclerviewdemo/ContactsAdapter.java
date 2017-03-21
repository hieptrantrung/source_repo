package com.demo.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nesquena.recyclerviewdemo.R;

import java.util.ArrayList;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Contact> mContacts = new ArrayList<>();

    public void addMoreContacts(List<Contact> newContacts) {
        int insertionPosition = mContacts.size();
        mContacts.addAll(newContacts);

        // OMIT to illustrate how the UI is independent from the dataset
        // and does not update automatically.
        notifyItemRangeInserted(insertionPosition, newContacts.size());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Contact contact = mContacts.get(position);

        TextView textView = viewHolder.tvName;
        textView.setText(contact.getName());

        Button button = viewHolder.tvHometown;

        if (contact.isOnline()) {
            button.setText("Message");

            // OMIT to show that rows are recycled.
            //
            // Scrolling past the midpoint of the list (when contacts are listed as offline)
            // and scrolling back up should result in some buttons being inadvertently disabled.
            button.setEnabled(true);
        } else {
            button.setText("Offline");
            button.setEnabled(false);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public Button tvHometown;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.contact_name);
            this.tvHometown = (Button) itemView.findViewById(R.id.message_button);
            // Store the context
            this.context = context;
            // Attach a click listener to the entire row view
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                //Contact user = mContacts.get(position);
                // We can access the data within the views
                Toast.makeText(context, tvName.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
