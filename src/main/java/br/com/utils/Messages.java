package br.com.utils;

public final class Messages {

    // Restaurant menus and display messages

    public static final String FOOD_MENU =
            "=========== CARDÁPIO ===========\n" +
            "1 - X-Burger (R$ 16,00)\n" +
            "2 - X-Salada (R$ 18,00)\n" +
            "3 - X-Bacon (R$ 22,00)\n";

    public static final String MAIN_MENU =
            "========= RESTAURANTE ==========\n" +
            "1 - Registrar novo pedido\n" +
            "2 - Listar pedidos do dia\n" +
            "3 - Consultar pedido\n" +
            "4 - Modificar pedido\n" +
            "5 - Fechar restaurante\n" +
            "0 - Sair\n";

    public static final String MODIFICATION_MENU =
            "======= MODIFICAR PEDIDO =======\n" +
            "1 - Acrescentar item\n" +
            "2 - Remover item\n" +
            "3 - Cancelar pedido\n" +
            "0 - Voltar\n";

    public static final String DAILY_CLOSING =
            "====== FECHAMENTO DO DIA =======\n" +
            "Total vendido: R$ %.2f%n";

    public static final String DIFFERENT_COMBINATIONS =
            "Combinações diferentes: %d%n";

    public static final String ASK_CUSTOMIZATION =
            "Deseja adicionar extras ou retirar ingredientes? (1 - Sim | 0 - Não): ";

    public static final String ASK_ADD_ANOTHER =
            "Adicionar outro lanche? (1 - Sim | 0 - Não): ";

    public static final String ASK_CHEDDAR =
            "Adicionar cheddar(+ R$ 4,00)? (1 - Sim | 0 - Não): ";

    public static final String ASK_EXTRA_BACON =
            "Adicionar bacon extra(+ R$ 6,00)? (1 - Sim | 0 - Não): ";

    public static final String REMOVE_CHEESE =
            "Retirar queijo? (1 - Sim | 0 - Não): ";

    public static final String REMOVE_LETTUCE =
            "Retirar alface? (1 - Sim | 0 - Não): ";

    public static final String REMOVE_TOMATO =
            "Retirar tomate? (1 - Sim | 0 - Não): ";

    public static final String REMOVE_ONION =
            "Retirar cebola? (1 - Sim | 0 - Não): ";

    public static final String ENTER_ITEM_NUMBER =
            "Digite o número do item que deseja remover: ";
}
