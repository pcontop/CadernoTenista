package br.com.pcontop.CadernoOlheiro.model.dao.time;

import br.com.pcontop.CadernoOlheiro.bean.Time;

import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 09/11/13
 * time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public interface TimeDAO {

    public Time get(String id);
    public void remova(Time time);

}
