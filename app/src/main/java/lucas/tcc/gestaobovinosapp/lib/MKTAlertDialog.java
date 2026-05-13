package lucas.tcc.gestaobovinosapp.lib;

import android.app.Activity;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MKTAlertDialog {

    private SweetAlertDialog pDialog;
    private Activity activity;
    private int tipoMensagem;
    private String titulo;
    private String mensagem;
    private boolean cancelable;

    public static final int TIPO_NORMAL = 0;
    public static final int TIPO_ERRO = 1;
    public static final int TIPO_SUCESSO = 2;
    public static final int TIPO_ALERTA = 3;
    public static final int TIPO_CUSTOMIZADO = 4;
    public static final int TIPO_PROGRESSO = 5;

    private SweetAlertDialog.OnSweetClickListener confirmListener;
    private SweetAlertDialog.OnSweetClickListener cancelListener;
    private SweetAlertDialog.OnSweetClickListener neutroListener;

    public MKTAlertDialog(final Activity activity, final int tipoMensagem, final String titulo, final String mensagem, final boolean cancelable) {
        this
        (
            activity,
            tipoMensagem,
            titulo,
            mensagem,
            cancelable,
            null
        );
    }

    public MKTAlertDialog(final Activity activity, final int tipoMensagem, final String titulo, final String mensagem, final boolean cancelable, SweetAlertDialog.OnSweetClickListener confirmListener) {
        this
        (
            activity,
            tipoMensagem,
            titulo,
            mensagem,
                cancelable,
            confirmListener,
            null
        );
    }

    public MKTAlertDialog(final Activity activity, final int tipoMensagem, final String titulo, final String mensagem, final boolean cancelable, SweetAlertDialog.OnSweetClickListener confirmListener, SweetAlertDialog.OnSweetClickListener cancelListener) {
        this
        (
                activity,
                tipoMensagem,
                titulo,
                mensagem,
                cancelable,
                confirmListener,
                cancelListener,
                null
        );
    }

    public MKTAlertDialog(final Activity activity, final int tipoMensagem, final String titulo, final String mensagem, final boolean cancelable, SweetAlertDialog.OnSweetClickListener confirmListener, SweetAlertDialog.OnSweetClickListener cancelListener, SweetAlertDialog.OnSweetClickListener neutroListener) {
        this.activity = activity;
        this.tipoMensagem = tipoMensagem;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.cancelable = cancelable;
        this.confirmListener = confirmListener;
        this.cancelListener = cancelListener;
        this.neutroListener = neutroListener;
        pDialog = new SweetAlertDialog(activity, tipoMensagem);
    }

    public void setButtonConfirmText(final String texto) {
        pDialog.setConfirmText(texto);
    }

    public void setButtonCancelText(final String texto) {
        pDialog.setCancelText(texto);
    }

    public void setButtonNeutroText(final String texto) {
        pDialog.setNeutralText(texto);
    }

    public void Show() {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(titulo);
        pDialog.setContentText(mensagem);
        pDialog.setCancelable(cancelable);
        pDialog.setConfirmClickListener(confirmListener);
        pDialog.setCancelClickListener(cancelListener);
        pDialog.setNeutralClickListener(neutroListener);
        pDialog.show();
    }

    public void setContentText(String contenteMessage) {
        pDialog.setContentText(contenteMessage);
    }

    public void Close() {
        pDialog.dismissWithAnimation();
    }
}
