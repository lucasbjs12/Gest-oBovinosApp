package lucas.tcc.gestaobovinosapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import lucas.tcc.gestaobovinosapp.R;
import lucas.tcc.gestaobovinosapp.model.InvernadaModel;

public class InvernadaAdapter extends RecyclerView.Adapter<InvernadaAdapter.ViewHolder> {

    private final Context context;
    private final List<InvernadaModel> invernadas;

    public InvernadaAdapter(Context context, List<InvernadaModel> invernadas) {
        this.context = context;
        this.invernadas = invernadas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_invernada, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InvernadaModel inv = invernadas.get(position);
        holder.tvDescricao.setText(inv.getDescricao());
        holder.tvObservacoes.setText(inv.getObservacoes());

        // Carrega imagem (ou ícone padrão)
        if (inv.getUrlFoto() != null && !inv.getUrlFoto().isEmpty()) {
            /**Glide.with(context)
                    .load(inv.getUrlFoto())
                    .placeholder(R.drawable.ic_pasto)
                    .into(holder.imgInvernada);*/
            Picasso.get().load(inv.getUrlFoto()).into(holder.imgInvernada);
        } else {
            holder.imgInvernada.setImageResource(R.drawable.ic_pasto);
        }
    }

    @Override
    public int getItemCount() {
        return invernadas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgInvernada;
        TextView tvDescricao, tvObservacoes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgInvernada = itemView.findViewById(R.id.imgInvernada);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvObservacoes = itemView.findViewById(R.id.tvObservacoes);
        }
    }
}
