package inovatec.br.com.gerenciadordegastos;

/**
 * Created by gustavo on 28/10/2016.
 */
public class Despesa {
    String valor;
    String descricao;
    String tipo;

    public Despesa(String valor, String descricao, String tipo) {
        this.valor = valor;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
