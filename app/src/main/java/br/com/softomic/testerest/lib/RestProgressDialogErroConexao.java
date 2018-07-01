package br.com.softomic.testerest.lib;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import br.com.softomic.testerest.R;

public class RestProgressDialogErroConexao {
    private static ProgressDialog progressDialog;

    public static void show(final RestActivity restActivity) {
        progressDialog = new ProgressDialog(restActivity.getActivity());
        progressDialog.setTitle(restActivity.getActivity().getString(R.string.msg_dialog_titulo));
        progressDialog.setMessage(restActivity.getActivity().getString(R.string.msg_conexao_perdida));
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                restActivity.getActivity().getString(R.string.msg_btn_sair), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        try {
                            Intent intent = new Intent(restActivity.getActivity(), Class.forName(RestConnection.LOGIN_CLASS.getName()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            restActivity.getActivity().startActivity(intent);
                            restActivity.getActivity().finish();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }

                });

        progressDialog.show();
    }

    public static void dismiss() {
        progressDialog.dismiss();
    }
}
