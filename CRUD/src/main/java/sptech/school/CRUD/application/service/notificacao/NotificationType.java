package sptech.school.CRUD.application.service.notificacao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    // Transferências
    TRANSFERENCIA_CRIADA(
            "transferencia",
            "CRIADO",
            "Transferência realizada com sucesso!",
            "Nova Transferência Realizada",
            "Uma nova transferência foi criada no sistema."
    ),

    // Ordens de Compra
    ORDEM_COMPRA_CRIADA(
            "ordem_compra",
            "CRIADO",
            "Nova ordem de compra registrada!",
            "Ordem de Compra Criada",
            "Uma nova ordem de compra foi registrada no sistema."
    ),

    ORDEM_COMPRA_APROVADA(
            "ordem_compra",
            "APROVADO",
            "Ordem de compra aprovada!",
            "Ordem de Compra Aprovada",
            "Uma ordem de compra foi aprovada."
    ),

    // Fornecedores
    FORNECEDOR_CADASTRADO(
            "cadastro_fornecedor",
            "CRIADO",
            "Novo fornecedor cadastrado no sistema!",
            "Fornecedor Cadastrado",
            "Um novo fornecedor foi adicionado ao sistema."
    ),

    FORNECEDOR_ATUALIZADO(
            "cadastro_fornecedor",
            "ATUALIZADO",
            "Dados de fornecedor atualizados!",
            "Fornecedor Atualizado",
            "Os dados de um fornecedor foram atualizados."
    ),

    // Usuários
    USUARIO_CADASTRADO(
            "cadastro_usuario",
            "CRIADO",
            "Novo usuário cadastrado no sistema!",
            "Usuário Cadastrado",
            "Um novo usuário foi cadastrado no sistema."
    ),

    USUARIO_ATUALIZADO(
            "cadastro_usuario",
            "ATUALIZADO",
            "Dados de usuário atualizados!",
            "Usuário Atualizado",
            "Os dados de um usuário foram atualizados."
    ),

    // Estoque
    ESTOQUE_ATUALIZADO(
            "cadastro_estoque",
            "ATUALIZADO",
            "Estoque atualizado!",
            "Atualização de Estoque",
            "O estoque foi atualizado com uma nova ordem de compra."
    ),

    ESTOQUE_BAIXO(
            "cadastro_estoque",
            "ALERTA",
            "Alerta: Estoque baixo!",
            "Alerta de Estoque Baixo",
            "O estoque de um item está abaixo do nível mínimo."
    );

    private final String entity;
    private final String eventType;
    private final String mensagemToast;
    private final String assuntoEmail;
    private final String mensagemEmail;

    /**
     * Monta a mensagem completa de email com detalhes
     */
    public String getMensagemEmailCompleta(String idReferencia, String detalhes) {
        return String.format(
                "%s\n\n" +
                        "ID de Referência: %s\n" +
                        "%s\n\n" +
                        "Esta é uma notificação automática do sistema.",
                this.mensagemEmail,
                idReferencia,
                detalhes != null ? "Detalhes: " + detalhes : ""
        );
    }
}
