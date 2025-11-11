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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import lucas.tcc.gestaobovinosapp.adapter.BovinoAdapter;
import lucas.tcc.gestaobovinosapp.api.RetrofitClient;
import lucas.tcc.gestaobovinosapp.api.services.ApiService;
import lucas.tcc.gestaobovinosapp.lib.MKTAlertDialog;
import lucas.tcc.gestaobovinosapp.model.BovinoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BovinoActivity extends Activity {

    private RecyclerView rvBovinos;
    private FloatingActionButton fabAdd;
    private ProgressBar progress;
    private BovinoAdapter adapter;
    private List<BovinoModel> lista = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bovino_list);

        rvBovinos = findViewById(R.id.rvBovinos);
        fabAdd = findViewById(R.id.fabAddBovino);
        progress = findViewById(R.id.progressLoading);

        fabAdd.setOnClickListener(v ->
                startActivity(new Intent(this, BovinoCadastroActivity.class))
        );

        carregarBovinos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarBovinos();
    }

    private void carregarBovinos() {
        progress.setVisibility(View.VISIBLE);

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        api.listarBovinos().enqueue(new Callback<BovinoModel[]>() {
            @Override
            public void onResponse(Call<BovinoModel[]> call, Response<BovinoModel[]> response) {
                progress.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    lista = new ArrayList<>(Arrays.asList(response.body()));
                    adapter = new BovinoAdapter(BovinoActivity.this, lista);
                    rvBovinos.setAdapter(adapter);
                } else {
                    new MKTAlertDialog
                            (
                                    BovinoActivity.this,
                                    MKTAlertDialog.TIPO_ALERTA,
                                    "Informação",
                                    "Nenhum bovino encontrado!",
                                    false,
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    }
                            ).Show();
                }
            }

            @Override
            public void onFailure(Call<BovinoModel[]> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(BovinoActivity.this,
                        "Erro ao carregar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
