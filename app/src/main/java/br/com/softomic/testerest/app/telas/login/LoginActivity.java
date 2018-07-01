package br.com.softomic.testerest.app.telas.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.softomic.testerest.R;
import br.com.softomic.testerest.app.telas.principal.PrincipalActivity;
import br.com.softomic.testerest.lib.RestActivity;
import br.com.softomic.testerest.lib.RestConnection;

public class LoginActivity extends AppCompatActivity implements RestActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.login_btn);

        RestConnection.configurar(
                true,
                25000,//considerar tempo previsto em demais serviços
                5000,
                "http",
                "10.0.0.100",
                "8080",
                "servicosteste",
                LoginActivity.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPrincipal();
            }
        });
    }

    private void abrirPrincipal() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void pararServicos() {}

    @Override
    public void onBackPressed() {
        pararServicos();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        pararServicos();
        super.onStop();
    }

    @Override
    public void finish() {
        pararServicos();
        super.finish();
    }
}
