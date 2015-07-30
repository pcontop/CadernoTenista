package br.com.pcontop.CadernoTenista.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.control.FabricaController;
import br.com.pcontop.CadernoTenista.control.OlheiroController;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 06/11/13
 * time: 21:58
 * To change this template use File | Settings | File Templates.
 */
public class DialogEventos extends DialogFragment {

    private OlheiroController olheiroController = FabricaController.getOlheiroController(null);
    private Set<Integer> mSelectedItems;

    public DialogEventos(){
        olheiroController = FabricaController.getOlheiroController(null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        mSelectedItems = getSelectedPassesPositions();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.escolha_eventos)
                .setPositiveButton(getText(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        executarAcao();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .setMultiChoiceItems(getArrayEventosJogador(), getTiposEventosJogadorSelecionados(), getOnMultiChoiceActionListener());
        return builder.create();
    }

    private Set<Integer> getSelectedPassesPositions() {
        return olheiroController.getSelectedTiposEventos();
    }

    private void setSelectedTiposEventos(Set<Integer> selectedTiposEventos){
        olheiroController.setSelectedTiposEventos(selectedTiposEventos);

    }

    private boolean[] getTiposEventosJogadorSelecionados(){
        return olheiroController.getTiposEventosJogadorSelecionados();
    }

    private DialogInterface.OnMultiChoiceClickListener getOnMultiChoiceActionListener() {
        return new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which,
                                boolean isChecked) {
                if (isChecked) {
                    // If the user checked the item, add it to the selected items
                    mSelectedItems.add(which);
                } else if (mSelectedItems.contains(which)) {
                    // Else, if the item is already in the array, remove it
                    mSelectedItems.remove(Integer.valueOf(which));
                }
            }
        };
    }

    private CharSequence[] getArrayEventosJogador(){
        return olheiroController.getArrayTiposEventosJogador();
    }

    protected void executarAcao(){
        setSelectedTiposEventos(mSelectedItems);
        Toast.makeText(getActivity(), "Eventos Selecionados", Toast.LENGTH_SHORT).show();
    }


}
