/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import models.ModelEmpresa;
import models.ModelProduto;
import utils.Database;
import views.Gerador;

/**
 *
 * @author Rodrigo
 */
public class ControllerGerador {

    public List<ModelProduto> getProdutos(StringBuilder query) throws SQLException {
        List<ModelProduto> produtos = new ArrayList<>();
        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement(query.toString())) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    ModelProduto produto = new ModelProduto();
                    produto.setIdEmpresa(rs.getString("IDEMPRESA"));
                    produto.setIdProduto(rs.getString("IDPRODUTO"));
                    produto.setDescricao(rs.getString("DESCRICAO"));
                    produto.setBarras(rs.getString("BARRAS"));
                    produto.setForaDeLinha(rs.getString("FORADELINHA").equals("S"));
                    produtos.add(produto);
                }
            }
        }
        return produtos;
    }

    public void setProdutos(List<ModelProduto> produtos, JProgressBar progressBar, int progresso, int totalProgresso) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE PRODUTOS SET BARRAS = ?");
        query.append("WHERE IDEMPRESA = ? AND IDPRODUTO = ?");
        try (Connection conn = new Database().getConnection();) {
            conn.setAutoCommit(false);
            for (ModelProduto produto : produtos) {
                progressBar.setValue(progresso);
                Gerador.setInfoProgresso(totalProgresso, progresso);
                progresso++;
                try (PreparedStatement pst = conn.prepareStatement(query.toString())) {
                    pst.setString(1, produto.getBarras());
                    pst.setString(2, produto.getIdEmpresa());
                    pst.setString(3, produto.getIdProduto());
                    pst.execute();
                } catch (Exception ex) {
                    if (!conn.getAutoCommit()) {
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                }
            }
            if (!conn.getAutoCommit()) {
                conn.commit();
                conn.setAutoCommit(true);
            }
        }
    }

    public List<ModelEmpresa> getEmpresas() {
        List<ModelEmpresa> empresas = new ArrayList<>();
        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT IDEMPRESA, NOME FROM EMPRESAS "
                        + "ORDER BY NOME ASC")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    ModelEmpresa empresa = new ModelEmpresa();
                    empresa.setIdEmpresa(rs.getString("IDEMPRESA"));
                    empresa.setNome(rs.getString("NOME"));
                    empresas.add(empresa);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Problemas ao obter empresas, motivo:\n"
                    + ex.getMessage());
            ex.printStackTrace();
        }
        return empresas;
    }
}
