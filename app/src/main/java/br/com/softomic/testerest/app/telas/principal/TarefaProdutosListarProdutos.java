package br.com.softomic.testerest.app.telas.principal;

import android.util.Log;

import br.com.softomic.testerest.app.models.Produto;
import br.com.softomic.testerest.app.services.produtos.ProdutosListarService;
import br.com.softomic.testerest.lib.RestConnection;
import br.com.softomic.testerest.lib.RestTarefa;
import retrofit2.Call;
import retrofit2.Response;

public class TarefaProdutosListarProdutos implements RestTarefa{
    private PrincipalActivity activity;

    public TarefaProdutosListarProdutos(PrincipalActivity activity) {
        this.activity = activity;
    }

    @Override
    public Call getCall() {
        return new RestConnection().getRetrofit().create(ProdutosListarService.class).listar();
    }

    @Override
    public void retornoComSucesso(Response response) {
        Produto retorno = (Produto) response.body();

        activity.retornoTarefaListarPrdutos(retorno);
        Log.i("TAREFA_REST", "Tarefa listar produtos executada com sucesso!");
    }

    @Override
    public void retornoSemSucesso(Response response) {
        Log.e("TAREFA_REST", "Tarefa listar produtos BAD REQUEST!");
    }

    @Override
    public void retornoComErro(String message) {
        Log.e("TAREFA_REST", "Tarefa listar produtos ERRO " + message);
    }
}
