package com.volunteam.mobilebijb.travelDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.CircleTransform;

import java.util.List;

/**
 * Created by iqbal on 5/11/2018.
 */

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.HolderData> {

    private List<Komentar> postData;

    public KomentarAdapter(List<Komentar> postData) {
        this.postData = postData;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_komentar, parent, false);
        HolderData holderData = new HolderData(v);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        Komentar post = postData.get(position);
        holder.txNama.setText(post.getNama());
        holder.txKomentar.setText(post.getDescription());
        holder.txTanggal.setText(post.getTanggal());

        if (post.getImage()==null){
            Picasso.with(holder.itemView.getContext())
                    .load(R.drawable.ic_profile)
                    .placeholder(R.drawable.ic_profile)
                    .error(android.R.color.transparent)
                    .transform(new CircleTransform())
                    .into(holder.imgPost);
        }else{
            Picasso.with(holder.itemView.getContext())
                    .load(post.getImage())
                    .placeholder(R.color.colorPrimaryDark)
                    .error(android.R.color.transparent)
                    .transform(new CircleTransform())
                    .into(holder.imgPost);
        }
        holder.txScore.setText(String.valueOf(post.getScore()));
        holder.id = Integer.valueOf(post.getId_user());
    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{

        int id;
        public TextView txNama;
        public ImageView imgPost;
        public Context context;
        public TextView txKomentar;
        public TextView txTanggal;
        public TextView txScore;

        public HolderData(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txNama = (TextView) itemView.findViewById(R.id.tx_nama);
            txKomentar = (TextView) itemView.findViewById(R.id.tx_komentar);
            txTanggal = (TextView) itemView.findViewById(R.id.tx_tanggal);
            txScore = (TextView) itemView.findViewById(R.id.tx_score_komen);
            imgPost = (ImageView) itemView.findViewById(R.id.image_komen);
            txNama.setOnClickListener(this);
            imgPost.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Context context = view.getContext();
//            Intent intent = new Intent(context, ProfileActivity.class);
//            intent.putExtra("idUser", String.valueOf(id));
//            context.startActivity(intent);
        }
    }

}
