/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import model.Parametro;
import services.ParametrosService;

/**
 *
 * @author charles
 */
public class Parametros {

    private static Parametros instance;

    private Map<String, String> valoresCache;
    private ParametrosService service;

    static {
        instance = new Parametros();
    }

    public Parametros() {
        if (instance != null) {
            throw new RuntimeException("Classe deve ser instanciada sómente uma vez, use o Parametros.getInstance");
        }
        valoresCache = new HashMap<>();
        service = new ParametrosService();
    }

    public static Parametros getInstance() {
        return instance;
    }

    private String valueKey(String key) {
        return service.findById(key).getValor();
    }

    public String getValue(String key) {
        if (!valoresCache.containsKey(key)) {
            valoresCache.put(key, valueKey(key));
        }
        return valoresCache.get(key);
    }

    public void validaParametro(String key, Function<String, Boolean> validar, Function<String, String> createIfNotExists) {
        String s = null;
        try {
            s = valueKey(key);

        } catch (Exception e) {
        }
        if (s == null) {
            model.Parametro param = new Parametro();
            param.setChave(key);
            param.setValor(createIfNotExists.apply(null));
            service.insert(param);
        } else if (!validar.apply(s)) {
            model.Parametro param = new Parametro();
            param.setChave(key);
            param.setValor(createIfNotExists.apply(s));
            service.update(param);
        }

    }
}
