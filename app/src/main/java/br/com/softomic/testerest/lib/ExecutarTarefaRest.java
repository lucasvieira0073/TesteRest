package br.com.softomic.testerest.lib;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import br.com.softomic.testerest.R;
import retrofit2.Call;
import retrofit2.Response;


public class ExecutarTarefaRest extends AsyncTask<Void, EnumProgressOptions, Integer> {
    private  RestTarefa         restTarefa;
    private  RestActivity       restActivity;
    private  boolean            loop;
    private  int                millis;
    private  ProgressBar progressBar;
    public   boolean            conexao;
    private  boolean            utilizaProgressbar;
    private  Response           responseResult;
    private  String             messageResult;
    private  Call               call;

    public ExecutarTarefaRest() {}

    public ExecutarTarefaRest(RestActivity restActivity, boolean loop, int millis, ProgressBar progressBar,
                              RestTarefa restTarefa) {

        this.restActivity     = restActivity;
        this.loop             = loop;
        this.millis           = millis;
        this.progressBar      = progressBar;
        this.restTarefa       = restTarefa;
        this.conexao          = true;

        if(progressBar != null) {
            this.utilizaProgressbar = true;
        }else{
            this.utilizaProgressbar = false;
        }

    }

    //metodo executado na thread UI antes de executar o codigo assincrono
    @Override
    protected void onPreExecute() {
        if(utilizaProgressbar) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    //metodo que sera executado na thread UI
    @Override
    protected void onProgressUpdate(EnumProgressOptions... values) {

        //recebe a acao a ser executada e executa o metodo de acordo com o resultado
        switch (values[0]) {
            // caso o status HTTP seja na casa dos  200
            case SUCESSO : {
                restTarefa.retornoComSucesso(responseResult);
                if(utilizaProgressbar) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                break;
            }

            // caso o status HTTP seja na casa dos 400
            case BAD_REQUEST : {
                if(utilizaProgressbar) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                restTarefa.retornoSemSucesso(responseResult);
                break;
            }

            // caso o status HTTP seja na casa dos 500
            case ERRO_CONEXAO : {
                if(utilizaProgressbar) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                restTarefa.retornoComErro(messageResult);
                break;
            }

            //exibe a progressDialog informado a perca de conexão
            case EXIBE_PROGRESS_DIALOG : {
                if(utilizaProgressbar) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                if(restActivity.getActivity().getClass().getName().equals(RestConnection.LOGIN_CLASS.getName())) {
                    DialogWarning.show(restActivity.getActivity(), restActivity.getActivity().getString(R.string.msg_dialog_titulo),
                            restActivity.getActivity().getString(R.string.login_impossivel));
                    restActivity.pararServicos();
                }else{
                    exibeProgressDialog();
                }
                break;
            }

            //fecha a progressDialog quando a conexão e retomada
            case FECHA_PROGRESS_DIALOG : {
                fechaProgressDialog();
                break;
            }

            default:
                break;
        }

    }

    //codigo assincrono nao realizado na thread UI ( nao e possivel acessar elementos de interface )
    @Override
    protected Integer doInBackground(Void... objects) {

        //servico chamado em uma asyncTask
        executarServico();

        return null;
    }

    //executado na finalização da AsyncTask ( na thread UI )
    //nao utilizado pois e feita chamadas para a UI antes da finalizacao da Task
    @Override
    protected void onPostExecute(Integer value) {}


    public boolean executarServico() {

        if(isCancelled()) {
            return false;
        }

        do {
            this.call = restTarefa.getCall();

            try {
                Response response = call.execute();

                if (response.isSuccessful()) {
                    if(!conexao) {
                        conexao = true;
                        publishProgress(EnumProgressOptions.FECHA_PROGRESS_DIALOG);
                    }
                    responseResult = response;
                    publishProgress(EnumProgressOptions.SUCESSO);
                } else {
                    if(!conexao) {
                        conexao = true;
                        publishProgress(EnumProgressOptions.FECHA_PROGRESS_DIALOG);
                    }
                    responseResult = response;
                    publishProgress(EnumProgressOptions.BAD_REQUEST);
                }
            } catch (Exception e) {
                if(conexao) {
                    conexao = false;
                    messageResult = e.getMessage();
                    publishProgress(EnumProgressOptions.ERRO_CONEXAO);
                    publishProgress(EnumProgressOptions.EXIBE_PROGRESS_DIALOG);
                }
            }

            if(!conexao) {
                try {
                    Thread.sleep(RestConnection.RECONEXAO);
                } catch (InterruptedException e) {
                    Log.i("REST", "O SERVICO FOI CANCELADO");
                }
                executarServico();
                return false;
            }

            if(loop) {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    Log.i("REST", "O SERVICO FOI CANCELADO");
                }
            }
        }while(loop);

        return true;
    }

    // metodo responsavel por exibir a barra de dialog de progresso
    public void exibeProgressDialog() {
        RestProgressDialogErroConexao.show(restActivity);
    }

    public void fechaProgressDialog() {
        RestProgressDialogErroConexao.dismiss();
    }

    //verifica se esta rodando um servico em loop
    public boolean estaRodando() {
        if((getStatus() == AsyncTask.Status.RUNNING || getStatus() == Status.PENDING ) && !isCancelled()) {
            return true;
        }else{
            return false;
        }
    }

    public void parar() {
        this.loop = false;

        if(utilizaProgressbar) {
            progressBar.setVisibility(View.INVISIBLE);
        }

        if(this.call != null && !this.call.isCanceled()) {
            this.call.cancel();
            this.call = null;
        }

        this.cancel(true);
    }
}
