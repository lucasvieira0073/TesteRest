package br.com.softomic.testerest.lib;

import retrofit2.Call;
import retrofit2.Response;

public interface RestTarefa {
    Call getCall();
    void retornoComSucesso(Response response);
    void retornoSemSucesso(Response response);
    void retornoComErro(String message);
}
