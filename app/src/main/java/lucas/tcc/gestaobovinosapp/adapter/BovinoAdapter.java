package lucas.tcc.gestaobovinosapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import lucas.tcc.gestaobovinosapp.EventoSanitarioFormActivity;
import lucas.tcc.gestaobovinosapp.R;
import lucas.tcc.gestaobovinosapp.model.BovinoModel;

public class BovinoAdapter extends RecyclerView.Adapter<BovinoAdapter.ViewHolder> {

    private final Context context;
    private final List<BovinoModel> bovinos;

    public BovinoAdapter(Context context, List<BovinoModel> bovinos) {
        this.context = context;
        this.bovinos = bovinos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bovino, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BovinoModel b = bovinos.get(position);
        holder.tvNomeAnimal.setText(b.getNomeAnimal());
        holder.tvRaca.setText(b.getRaca());
        holder.tvBrinco.setText("Brinco: " + b.getNumeroBrinco());
        holder.tvInvernada.setText("Invernada: " + b.getInvernadaId()+" - "+b.getInvernadaDescricao());
        // 👇 Segurar por 5 segundos abre o cadastro de evento
        holder.principal.setOnLongClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("Novo Evento Sanitário")
                    .setMessage("Deseja criar um novo evento sanitário para o animal \"" + b.getNomeAnimal() + "\"?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        Intent intent = new Intent(context, EventoSanitarioFormActivity.class);
                        intent.putExtra("bovinoId", b.getId());
                        intent.putExtra("bovinoNome", b.getNomeAnimal());
                        context.startActivity(intent);
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return bovinos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeAnimal, tvRaca, tvBrinco, tvInvernada;
        LinearLayout principal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            principal = itemView.findViewById(R.id.principal);
            tvNomeAnimal = itemView.findViewById(R.id.tvNomeAnimal);
            tvRaca = itemView.findViewById(R.id.tvRaca);
            tvBrinco = itemView.findViewById(R.id.tvBrinco);
            tvInvernada = itemView.findViewById(R.id.tvInvernada);
        }
    }
}
