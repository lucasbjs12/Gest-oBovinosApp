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
import lucas.tcc.gestaobovinosapp.model.BovinoModel;
import lucas.tcc.gestaobovinosapp.model.LeituraRfidModel;

public class LeituraRfidAdapter extends RecyclerView.Adapter<LeituraRfidAdapter.ViewHolder> {

    private final Context context;
    private final List<BovinoModel> bovinos;
    private final List<LeituraRfidModel> leituras;

    public LeituraRfidAdapter(Context context, List<BovinoModel> bovinos, List<LeituraRfidModel> leituras) {
        this.context = context;
        this.bovinos = bovinos;
        this.leituras = leituras;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_leitura_rfid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BovinoModel b = bovinos.get(position);
        LeituraRfidModel leitura = leituras.get(position);

        holder.tvNomeAnimal.setText(b.getNomeAnimal());
        holder.tvRaca.setText(b.getRaca());

        if (leitura == null) {
            holder.tvStatus.setText("❌ Não foi encontrado na Invernada");
            holder.tvInfoAdicional.setText("");
            holder.card.setCardBackgroundColor(context.getColor(R.color.red));
        } else {
            holder.tvStatus.setText("✅ Última leitura detectada");
            holder.tvInfoAdicional.setText("📡 " + leitura.getAntena() +
                    "   🕒 " + leitura.getHoraFormatada());
            holder.card.setCardBackgroundColor(context.getColor(R.color.teal_700));
        }
    }

    @Override
    public int getItemCount() {
        return bovinos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeAnimal, tvRaca, tvStatus, tvInfoAdicional;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeAnimal = itemView.findViewById(R.id.tvNomeAnimal);
            tvRaca = itemView.findViewById(R.id.tvRaca);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvInfoAdicional = itemView.findViewById(R.id.tvInfoAdicional);
            card = (MaterialCardView) itemView;
        }
    }
}
