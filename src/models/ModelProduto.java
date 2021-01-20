/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Rodrigo
 */
public class ModelProduto {
    
    String idEmpresa;
    String idProduto;
    String barras;
    String descricao;
    boolean foraDeLinha;
    
    public ModelProduto() {
        
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getBarras() {
        return barras;
    }

    public void setBarras(String barras) {
        this.barras = barras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isForaDeLinha() {
        return foraDeLinha;
    }

    public void setForaDeLinha(boolean foraDeLinha) {
        this.foraDeLinha = foraDeLinha;
    }

    @Override
    public String toString() {
        return "ModelProduto{" + "idProduto=" + idProduto + ", barras=" + barras + ", descricao=" + descricao + ", foraDeLinha=" + foraDeLinha + '}';
    }
}
