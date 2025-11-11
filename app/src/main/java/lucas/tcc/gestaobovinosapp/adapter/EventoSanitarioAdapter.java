package lucas.tcc.gestaobovinosapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

import lucas.tcc.gestaobovinosapp.R;
import lucas.tcc.gestaobovinosapp.model.EventoResponse;

public class EventoSanitarioAdapter extends RecyclerView.Adapter<EventoSanitarioAdapter.ViewHolder> {

    private final Context context;
    private final List<EventoResponse> eventos;

    public EventoSanitarioAdapter(Context context, List<EventoResponse> eventos) {
        this.context = context;
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_evento_sanitario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventoResponse e = eventos.get(position);
        holder.tvTipo.setText(e.getTipo());
        holder.tvDescricao.setText(e.getDescricao() != null ? e.getDescricao() : "");
        holder.tvData.setText("📅 " + e.getDataEvento());
        holder.tvObservacoes.setText("Código do Bovino: "+e.getBovinoId());

        int color;
        switch (e.getTipo().toLowerCase()) {
            case "vacina": color = context.getColor(R.color.teal_700); break;
            case "diagnostico": color = context.getColor(R.color.blue_700); break;
            case "tratamento": color = context.getColor(R.color.orange_700); break;
            default: color = context.getColor(R.color.gray_700); break;
        }
        holder.card.setCardBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipo, tvDescricao, tvData, tvObservacoes;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipo = itemView.findViewById(R.id.tvTipoEvento);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvData = itemView.findViewById(R.id.tvData);
            card = (MaterialCardView) itemView;
            tvObservacoes = itemView.findViewById(R.id.tvObservacoes);
        }
    }
}
