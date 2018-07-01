package br.com.softomic.testerest.app.telas.secundaria;

import android.util.Log;

import br.com.softomic.testerest.app.models.Item;
import br.com.softomic.testerest.app.services.itens.ItensListarService;
import br.com.softomic.testerest.lib.RestConnection;
import br.com.softomic.testerest.lib.RestTarefa;
import retrofit2.Call;
import retrofit2.Response;

public class TarefaItensListarItens implements RestTarefa {
    private SecundariaActivity activity;

    public TarefaItensListarItens(SecundariaActivity activity) {
        this.activity = activity;
    }

    @Override
    public Call getCall() {
        return new RestConnection().getRetrofit().create(ItensListarService.class).listar();
    }

    @Override
    public void retornoComSucesso(Response response) {
        Item retorno = (Item) response.body();
        activity.retornoTarefaListarItens(retorno);
        Log.i("TAREFA_REST", "Tarefa listar itens executada com sucesso!");
    }

    @Override
    public void retornoSemSucesso(Response response) {
        Log.e("TAREFA_REST", "Tarefa listar itens BAD REQUEST!");
    }

    @Override
    public void retornoComErro(String message) {
        Log.e("TAREFA_REST", "Tarefa listar itens ERRO " + message);
    }
}
