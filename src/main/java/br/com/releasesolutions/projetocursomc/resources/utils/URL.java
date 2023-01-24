package br.com.releasesolutions.projetocursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static String decodeParam(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static List<Integer> decodeIntList(String s) {

        // String[] vet = s.split(",");
        // List<Integer> list = new ArrayList<>();

        /*
        for (int i = 0; i < vet.length; i++)
            list.add(Integer.parseInt(vet[i]));
        return list;
        */

        /*
         Também é possível percorrer o vetor utilizando foreach
         for (String s1 : vet) list.add(Integer.parseInt(s1));
         return list;
        */

        // O bloco acima tem o mesmo efeito da expressão lambda comentada abaixo.
        // return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());

        // ou ainda utilizando a expressão abaixo. Importante: utilizar o retorno apropriado.

        return Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
}
