package lucas.tcc.gestaobovinosapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lucas.tcc.gestaobovinosapp.adapter.LeituraRfidAdapter;
import lucas.tcc.gestaobovinosapp.api.RetrofitClient;
import lucas.tcc.gestaobovinosapp.api.services.ApiService;
import lucas.tcc.gestaobovinosapp.model.BovinoModel;
import lucas.tcc.gestaobovinosapp.model.LeituraRfidModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeituraRfidActivity extends Activity {
    private RecyclerView rvLeituras;
    private ProgressBar progress;
    private LeituraRfidAdapter adapter;
    private List<BovinoModel> bovinos = new ArrayList<>();
    private List<LeituraRfidModel> leituras = new ArrayList<>();
    private ApiService api;
    private Button btnRecarregarLeituras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitura_rfid);

        rvLeituras = findViewById(R.id.rvLeituras);
        progress = findViewById(R.id.progressLoading);
        btnRecarregarLeituras = findViewById(R.id.btnRecarregarLeituras);
        btnRecarregarLeituras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregarLeituras();
            }
        });

        api = RetrofitClient.getInstance().create(ApiService.class);
        carregarLeituras();
    }

    private void carregarLeituras() {
        progress.setVisibility(View.VISIBLE);

        api.listarBovinos().enqueue(new Callback<BovinoModel[]>() {
            @Override
            public void onResponse(Call<BovinoModel[]> call, Response<BovinoModel[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bovinos = new ArrayList<>(Arrays.asList(response.body()));
                    leituras = new ArrayList<>(bovinos.size());
                    for (int i = 0; i < bovinos.size(); i++) leituras.add(null);
                    verificarLeituras();
                } else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(LeituraRfidActivity.this, "Erro ao buscar bovinos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BovinoModel[]> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(LeituraRfidActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verificarLeituras() {
        final int total = bovinos.size();
        final int[] processados = {0};

        for (int i = 0; i < bovinos.size(); i++) {
            final int index = i;
            BovinoModel b = bovinos.get(i);

            api.buscarLeituraUltimaPorBovino(b.getId()).enqueue(new Callback<LeituraRfidModel>() {
                @Override
                public void onResponse(Call<LeituraRfidModel> call, Response<LeituraRfidModel> response) {
                    processados[0]++;
                    if (response.isSuccessful() && response.body() != null) {
                        leituras.set(index, response.body());
                    }
                    if (processados[0] == total) atualizarLista();
                }

                @Override
                public void onFailure(Call<LeituraRfidModel> call, Throwable t) {
                    processados[0]++;
                    if (processados[0] == total) atualizarLista();
                }
            });
        }
    }

    private void atualizarLista() {
        runOnUiThread(() -> {
            progress.setVisibility(View.GONE);
            adapter = new LeituraRfidAdapter(this, bovinos, leituras);
            rvLeituras.setAdapter(adapter);
        });
    }
}
