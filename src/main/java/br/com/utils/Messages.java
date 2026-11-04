package br.com.utils;

public final class Messages {

    // Restaurant menus and display messages
    public static final String FOOD_MENU =
            "\n\n================= COMIDAS =================\n" +
            "1 - Trifle da Rachel (R$ 22,00)\n" +
            "2 - Sanduíche MoistMaker do Ross (R$ 28,00)\n" +
            "3 - Doces da Monica (R$ 12,00)\n" +
            "4 - Cheesecake do Chão (R$ 18,00)\n" +
            "5 - Pizza do Joey (R$ 32,00)\n" +
            "6 - Biscoitos da Avó da Phoebe (R$ 10,00)\n" +
            "\n================ BEBIDAS =================\n" +
            "7 - Café Comum (R$6,00)\n" +
            "8 - Café Gigante do Central Perk (R$ 14,00)\n" +
            "9 - Margaritas do Ross (R$ 18,00)\n" +
            "0 - Voltar ao menu\n";

    public static final String MAIN_MENU =
            "1 - Registrar novo pedido\n" +
            "2 - Listar pedidos do dia\n" +
            "3 - Consultar pedido\n" +
            "4 - Modificar pedido\n" +
            "5 - Fechar restaurante\n" +
            "0 - Sair\n";

    public static final String MODIFICATION_MENU =
            "============= MODIFICAR PEDIDO ============\n" +
            "1 - Acrescentar item\n" +
            "2 - Remover item\n" +
            "3 - Cancelar pedido\n" +
            "0 - Voltar\n";

    // Input prompts
    public static final String SELECT_OPTION = "Escolha uma opção: ";
    public static final String ENTER_CUSTOMER_NAME = "Insira seu nome: ";
    public static final String ENTER_ORDER_NUMBER = "Digite o número do pedido: ";
    public static final String ENTER_ITEM_NUMBER = "Digite o número do item que deseja remover: ";
    public static final String ENTER_TIP = "Informe o valor da gorjeta (R$): ";
    public static final String RETURN_MENU = "0 - Voltar ao menu";

    // Daily report
    public static final String TOTAL_SALES = "Total de vendas: R$ %.2f%n";
    public static final String TOTAL_TIPS = "Total de gorjetas: R$ %.2f%n";
    public static final String TOTAL_CASH = "Total no caixa: R$ %.2f%n";
    public static final String SUBTOTAL = "Subtotal: R$ %.2f%n";
    public static final String TOTAL = "Total: R$ %.2f%n";
    public static final String DIFFERENT_COMBINATIONS = "Combinações diferentes: %d%n";
    public static final String DIFFERENT_BASE_ITEMS = "Produtos diferentes: %d%n";

    // Customization prompts
    public static final String ASK_CUSTOMIZATION = "Deseja adicionar extras ou retirar ingredientes? (1 - Sim | 0 - Não): ";
    public static final String ASK_ADD_ANOTHER = "Adicionar outro item? (1 - Sim | 0 - Não): ";
    public static final String ASK_LARGE_SIZE = "Deseja tamanho extra? (1 - Sim | 0 - Não): ";
    public static final String SELECT_EXTRA = "Escolha um adicional: ";
    public static final String EXTRA_ALREADY_SELECTED = "Esse adicional já foi selecionado.";

    private Messages() {
    }
}
