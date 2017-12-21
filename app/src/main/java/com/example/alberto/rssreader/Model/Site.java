package com.example.alberto.rssreader.Model;

/**
 * Created by Luca on 21/12/2017.
 */

public class Site {

    private String nome, url;

    public Site(String nome, String url){
        this.nome=nome;
        this.url=url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass().equals(Site.class) && ((Site)o).getUrl().equals(url)){
            return true;
        }
        return false;
    }
}
