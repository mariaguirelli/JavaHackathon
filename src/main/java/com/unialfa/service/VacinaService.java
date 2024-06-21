package com.unialfa.service;

import com.unialfa.dao.VacinaDao;
import com.unialfa.model.Vacina;

import java.util.Collections;
import java.util.List;

public class VacinaService {

    public void salvar(Vacina vacina) {
        try {
            VacinaDao dao = new VacinaDao();
            if (vacina.getId() == null) {
                dao.inserir(vacina);
            } else {
                dao.atualizar(vacina);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar Vacina: " + e.getMessage());
        }
    }

    public void deletar(int idVacina) {
        try {
            VacinaDao dao = new VacinaDao();
            dao.remover(idVacina);
        } catch (Exception e) {
            System.out.println("Erro ao deletar Vacina: " + e.getMessage());
        }
    }

    public List<Vacina> listarVacinas() {
        try {
            var dao = new VacinaDao();
            return dao.listarTodos();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
