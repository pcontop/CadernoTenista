package br.com.pcontop.CadernoOlheiro.bean;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 10/11/13
 * time: 18:13
 * To change this template use File | Settings | File Templates.
 */
public class UUIDProvider {
    public static String getNew(){
        UUID idOne = UUID.randomUUID();
        return idOne.toString();
    }
}
