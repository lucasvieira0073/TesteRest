package br.com.softomic.testerest.lib;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import br.com.softomic.testerest.R;

public class RestDialogErroSessao {
    public static void show(final RestActivity restActivity) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(restActivity.getActivity());
        dlgAlert.setTitle(restActivity.getActivity().getString(R.string.msg_dialog_titulo));
        dlgAlert.setMessage(restActivity.getActivity().getString(R.string.msg_sessao_expirada));
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(restActivity.getActivity().getString(R.string.msg_btn_sair), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
        dlgAlert.create();

        AlertDialog alert = dlgAlert.create();
        alert.show();
    }
}
