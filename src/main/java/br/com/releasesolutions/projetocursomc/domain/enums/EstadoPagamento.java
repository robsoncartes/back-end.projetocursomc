package br.com.releasesolutions.projetocursomc.domain.enums;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int codigo;
    private String descricao;

    EstadoPagamento(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento toEnum(Integer codigo) {

        if (codigo == null)
            return null;

        for (EstadoPagamento x : EstadoPagamento.values()) {
            if (codigo.equals(x.getCodigo()))
                return x;
        }

        throw new IllegalArgumentException("Id válido " + codigo);
    }
}
