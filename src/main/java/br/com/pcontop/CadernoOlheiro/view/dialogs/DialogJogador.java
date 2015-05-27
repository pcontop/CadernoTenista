package br.com.pcontop.CadernoOlheiro.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.Time;
import br.com.pcontop.CadernoOlheiro.control.FabricaController;
import br.com.pcontop.CadernoOlheiro.control.OlheiroController;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 06/11/13
 * time: 21:58
 * To change this template use File | Settings | File Templates.
 */
public class DialogJogador extends DialogFragment {

    private static OlheiroController olheiroController = FabricaController.getOlheiroController(null);
    private static ActionDialogJogador action;
    private Jogador jogador;
    EditText editNomeJogador;

    public DialogJogador(){

    }

    public DialogJogador(ActionDialogJogador action, Jogador jogador) {
        super();
        this.action=action;
        this.jogador = jogador;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Time time1 = olheiroController.getTime1();
        final Time time2 = olheiroController.getTime2();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        ViewGroup viewDialog = (ViewGroup) layoutInflater.inflate(R.layout.dialog_jogador_layout,null);
        editNomeJogador = (EditText) viewDialog.findViewById(R.id.dialog_jogador_nome_jogador);
        if (jogador!=null){
            editNomeJogador.setText(jogador.getNome());
            editNomeJogador.selectAll();
        }
        builder.setView(viewDialog);
        builder.setMessage(R.string.escolha_time)
                .setPositiveButton(time2.getNome(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        executarAcao(time2);
                    }
                })
                .setNegativeButton(time1.getNome(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        executarAcao(time1);
                    }
                });
        return builder.create();
    }

    protected void executarAcao(Time time){
        switch (action){
            case ADICIONAR:
                olheiroController.addNewJogador(editNomeJogador.getText().toString(), time);
                break;
            case EDITAR:
                olheiroController.editarJogador(jogador, editNomeJogador.getText().toString(), time);
                break;
            default:
        }
    }


}
