package br.com.pcontop.CadernoOlheiro.control.disabler;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 02/11/13
 * time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class Disabler implements ExecuteEnablerDisabler {
    @Override
    public void execute(CanBeDisabled canBeDisabled) {
        canBeDisabled.disable();
    }
}
