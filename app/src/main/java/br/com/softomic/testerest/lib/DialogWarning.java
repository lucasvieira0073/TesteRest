package br.com.softomic.testerest.lib;

import android.app.Activity;
import android.app.AlertDialog;

import br.com.softomic.testerest.R;

public class DialogWarning {

    public static  void show(Activity activity, String titulo, String mensagem) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(activity);
        dlgAlert.setTitle(titulo);
        dlgAlert.setMessage(mensagem);
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(activity.getString(R.string.msg_btn_ok), null);
        dlgAlert.create();
        AlertDialog alert = dlgAlert.create();
        alert.show();
    }
}
