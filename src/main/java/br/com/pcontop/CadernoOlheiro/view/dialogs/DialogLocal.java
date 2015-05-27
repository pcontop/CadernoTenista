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
import br.com.pcontop.CadernoOlheiro.bean.Localidade;
import br.com.pcontop.CadernoOlheiro.control.FabricaController;
import br.com.pcontop.CadernoOlheiro.control.OlheiroController;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 06/11/13
 * time: 21:58
 * To change this template use File | Settings | File Templates.
 */
public class DialogLocal extends DialogFragment {

    private static OlheiroController olheiroController = FabricaController.getOlheiroController(null);
    private Localidade localidade;
    EditText editLocal;

    public DialogLocal() {
        super();
        this.localidade = olheiroController.getLocal();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        ViewGroup viewDialog = (ViewGroup) layoutInflater.inflate(R.layout.dialog_local_layout,null);
        editLocal = (EditText) viewDialog.findViewById(R.id.dialog_local_local);
        if (localidade!=null){
            editLocal.setText(localidade.getDescricao());
            editLocal.selectAll();
        }
        builder.setView(viewDialog);
        builder.setMessage(getActivity().getString(R.string.escolha_localidade))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        executarAcao();
                    }
                })
                .setNegativeButton(getString(R.string.cancelar), null);
        return builder.create();
    }

    protected void executarAcao(){
        olheiroController.definaLocalidade(editLocal.getText().toString(), null, null);
    }


}
