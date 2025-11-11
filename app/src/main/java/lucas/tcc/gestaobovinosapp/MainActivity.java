package lucas.tcc.gestaobovinosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnInvernadas = findViewById(R.id.btnInvernadas);
        MaterialButton btnBovinos = findViewById(R.id.btnBovinos);
        MaterialButton btnLeituras = findViewById(R.id.btnLeituras);
        MaterialButton btnEventos = findViewById(R.id.btnEventos);

        btnInvernadas.setOnClickListener(v ->
                startActivity(new Intent(this, InvernadaActivity.class))
        );

        btnBovinos.setOnClickListener(v ->
                startActivity(new Intent(this, BovinoActivity.class))
        );

        btnLeituras.setOnClickListener(v ->
                startActivity(new Intent(this, LeituraRfidActivity.class))
        );

        btnEventos.setOnClickListener(v ->
                startActivity(new Intent(this, EventoSanitarioActivity.class))
        );
    }
}