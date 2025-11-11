package lucas.tcc.gestaobovinosapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lucas.tcc.gestaobovinosapp.R;
import lucas.tcc.gestaobovinosapp.adapter.InvernadaAdapter;
import lucas.tcc.gestaobovinosapp.api.RetrofitClient;
import lucas.tcc.gestaobovinosapp.api.services.ApiService;
import lucas.tcc.gestaobovinosapp.model.InvernadaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvernadaActivity extends Activity {
    private RecyclerView rvInvernadas;
    private FloatingActionButton fabAdd;
    private InvernadaAdapter adapter;
    private ProgressBar progress;
    private List<InvernadaModel> lista = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invernada_list);
        rvInvernadas = findViewById(R.id.rvInvernadas);
        fabAdd = findViewById(R.id.fabAddInvernada);
        progress = findViewById(R.id.progressLoading);

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, InvernadaCadastroActivity.class));
        });

        carregarInvernadas();
    }

    private void carregarInvernadas() {
        progress.setVisibility(View.VISIBLE);

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        api.listarInvernadas().enqueue(new Callback<InvernadaModel[]>() {
            @Override
            public void onResponse(Call<InvernadaModel[]> call, Response<InvernadaModel[]> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    lista = new ArrayList<>(Arrays.asList(response.body()));
                    adapter = new InvernadaAdapter(InvernadaActivity.this, lista);
                    rvInvernadas.setAdapter(adapter);
                } else {
                    Toast.makeText(InvernadaActivity.this, "Nenhuma invernada encontrada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InvernadaModel[]> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(InvernadaActivity.this, "Erro ao carregar invernadas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarInvernadas();
    }
}
